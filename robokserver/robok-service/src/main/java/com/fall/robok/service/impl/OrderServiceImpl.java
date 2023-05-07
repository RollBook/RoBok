package com.fall.robok.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fall.robok.config.WxConfig;
import com.fall.robok.mapper.BookMapper;
import com.fall.robok.mapper.CartMapper;
import com.fall.robok.mapper.OrderMapper;
import com.fall.robok.po.Book;
import com.fall.robok.po.Cart;
import com.fall.robok.po.Order;
import com.fall.robok.service.IOrderService;
import com.fall.robok.util.file.PayUtil;
import com.fall.robok.vo.CartPayInfo;
import com.fall.robok.vo.OrderOfPay;
import com.ijpay.core.IJPayHttpResponse;
import com.ijpay.core.enums.RequestMethodEnum;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.core.utils.DateTimeZoneUtil;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.enums.WxDomainEnum;
import com.ijpay.wxpay.enums.v3.BasePayApiEnum;
import com.ijpay.wxpay.enums.v3.OtherApiEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.*;
/**
 * @author 楠檀,
 * @date 2023/3/9,
 * @time 15:33,
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderMapper orderMapper;

    private final WxConfig wxConfig;

    private final PayUtil payUtil;

    private final BookMapper bookMapper;

    private final TransactionTemplate transactionTemplate;

    private final CartMapper cartMapper;


    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper , WxConfig wxConfig, PayUtil payUtil,
                            BookMapper bookMapper,TransactionTemplate transactionTemplate,CartMapper cartMapper){
        this.orderMapper = orderMapper;
        this.wxConfig = wxConfig;
        this.payUtil = payUtil;
        this.bookMapper = bookMapper;
        this.transactionTemplate = transactionTemplate;
        this.cartMapper = cartMapper;
    }

    @Override
    public List<Order> getOrder(String openid){
        return orderMapper.getOrder(openid);
    }

    /**
     * @param openid openid
     * @author Tan
     * @description 卖书书架，获取书本
     * @return: java.lang.String
     * @date 2022/9/27 14:45
     */
    @Override
    public List<Book> getSellBook(String openid){
        return orderMapper.getSellBook(openid);
    }

    @Override
    public Map doUnifiedOrder(OrderOfPay orderOfPay) throws Exception {
        //先写死1， 1就是1分钱，100=1元
        Integer price = orderOfPay.getPrice();
        //商户订单号(随机字符串),这个回调的时候会给回
        String orderSn = payUtil.generateNonceStr();
        //微信用户openId
        String openid = orderOfPay.getOpenid();
        //这个是微信小程序的appid
        String appid = wxConfig.getAppID();
        //商户号ID
        String mchId = wxConfig.getMchId();
        //设置下支付的超时时间
        String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
        Map<String, Object> data = payUtil.requestWxPayParam(mchId,orderSn, openid, price, appid, timeExpire);
        log.info("统一下单参数 {}", JSONUtil.toJsonStr(data));
        //这个是证书文件，先写死，后续调整成读取证书文件的服务器存放地址
        String privateKeyPath = "D:\\360极速浏览器下载\\WXCertUtil\\cert\\1621751822_20230422_cert\\apiclient_key.pem";
        //请求下单
        IJPayHttpResponse response = WxPayApi.v3(
                RequestMethodEnum.POST,
                WxDomainEnum.CHINA.toString(),
                BasePayApiEnum.JS_API_PAY.toString(),
                // 商户号
                mchId,
                // 获取证书序列号
                payUtil.getSerialNumber(),
                null,
                // 私钥
                privateKeyPath,
                // 请求参数
                JSONUtil.toJsonStr(data)
        );
        log.info("统一下单响应 {}", response);
        //这个就是小程序唤醒微信支付必要的那6个参数了
        Map<String, String> map =new HashMap<>();
        if (response.getStatus() == 200) {
            //这个是证书文件，先写死，后续调整成读取证书文件的服务器存放地址
            // 根据证书序列号查询对应的证书来验证签名结果
            String platformCertPath = "D:\\360极速浏览器下载\\WXCertUtil\\cert\\1621751822_20230422_cert\\cert.pem";
            //平台证书
            boolean verifySignature = WxPayKit.verifySignature(response,platformCertPath);
            log.info("verifySignature: {}", verifySignature);
            if (verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                // 私钥
                map = WxPayKit.jsApiCreateSign(appid, prepayId,privateKeyPath);
                log.info("唤起支付参数:{}", map);
            }
        }
        //todo 微信预支付的订单新入库，为了业务查询记录
        Order order = Order.builder()
                .orderId(orderSn)
                .createdTime(map.get("timeStamp"))
                .buyerId(orderOfPay.getOpenid())
                .sellerId(orderOfPay.getSellerId())
                .audit(0)
                .bookId(orderOfPay.getBookId()).build();
        transactionTemplate.execute(status -> {
            try {
                orderMapper.addOrder(order);
                bookMapper.updateBookAudit(3,orderOfPay.getBookId());
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
            return Boolean.TRUE;
        });

        return map;
    }

    @Override
    public String createPlatformCert() throws IOException {
        //商户号ID
        String mchId = wxConfig.getMchId();
        //这个商户号对应的那个V3秘钥
        String mckKey= wxConfig.getMckKey();
        // 获取平台证书列表
        try {
            //这个是证书文件，先写死，后续调整成读取证书文件的服务器存放地址
            String privateKeyPath = "D:\\360极速浏览器下载\\WXCertUtil\\cert\\1621751822_20230422_cert\\apiclient_key.pem";
            IJPayHttpResponse response = WxPayApi.v3(
                    RequestMethodEnum.GET,
                    WxDomainEnum.CHINA.toString(),
                    OtherApiEnum.GET_CERTIFICATES.toString(),
                    mchId,
                    payUtil.getSerialNumber(),
                    null,
                    privateKeyPath,
                    ""
            );

            String timestamp = response.getHeader("Wechatpay-Timestamp");
            String nonceStr = response.getHeader("Wechatpay-Nonce");
            String serialNumber = response.getHeader("Wechatpay-Serial");
            String signature = response.getHeader("Wechatpay-Signature");

            String body = response.getBody();
            int status = response.getStatus();

            log.info("serialNumber: {}", serialNumber);
            log.info("status: {}", status);
            log.info("body: {}", body);
            int isOk = 200;
            // 根据证书序列号查询对应的证书来验证签名结果
            String platformCertPath = "D:\\360极速浏览器下载\\WXCertUtil\\cert\\1621751822_20230422_cert\\cert.pem";
            if (status == isOk) {
                JSONObject jsonObject = JSONUtil.parseObj(body);
                JSONArray dataArray = jsonObject.getJSONArray("data");
                // 默认认为只有一个平台证书
                JSONObject encryptObject = dataArray.getJSONObject(0);
                JSONObject encryptCertificate = encryptObject.getJSONObject("encrypt_certificate");
                String associatedData = encryptCertificate.getStr("associated_data");
                String cipherText = encryptCertificate.getStr("ciphertext");
                String nonce = encryptCertificate.getStr("nonce");
                String serialNo = encryptObject.getStr("serial_no");
                //生成第四个证书文件
                final String platSerialNo = payUtil.savePlatformCert(associatedData,mckKey, nonce, cipherText, platformCertPath);
                log.info("平台证书序列号: {} serialNo: {}", platSerialNo, serialNo);
            }
            // 根据证书序列号查询对应的证书来验证签名结果
            boolean verifySignature = WxPayKit.verifySignature(response, platformCertPath);
            log.info("verifySignature:{}" + verifySignature);
            return body;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void callBack(HttpServletRequest request, HttpServletResponse response) {
        log.info("收到微信支付回调");
        Map<String, String> map = new HashMap<>(12);
        try {
            String timestamp = request.getHeader("Wechatpay-Timestamp");
            String nonce = request.getHeader("Wechatpay-Nonce");
            String serialNo = request.getHeader("Wechatpay-Serial");
            String signature = request.getHeader("Wechatpay-Signature");

            log.info("timestamp:{} nonce:{} serialNo:{} signature:{}", timestamp, nonce, serialNo, signature);
            String result = HttpKit.readData(request);
            log.info("支付通知密文 {}", result);
            // 根据证书序列号查询对应的证书来验证签名结果
            String platformCertPath = "D:\\360极速浏览器下载\\WXCertUtil\\cert\\1621751822_20230422_cert\\cert.pem";
            //这个商户号对应的那个V3秘钥
            String mckKey= wxConfig.getMckKey();
            System.out.println(mckKey);
            //需要通过证书序列号查找对应的证书，verifyNotify 中有验证证书的序列号
            String plainText = WxPayKit.verifyNotify(serialNo, result, signature, nonce, timestamp,mckKey, platformCertPath);
            log.info("支付通知明文 {}", plainText);
            //这个就是具体的业务情况
            payUtil.savePayPlainText(plainText);
            //回复微信
            if (StrUtil.isNotEmpty(plainText)) {
                response.setStatus(200);
                map.put("code", "SUCCESS");
                map.put("message", "SUCCESS");
            } else {
                response.setStatus(500);
                map.put("code", "ERROR");
                map.put("message", "签名错误");
            }
            response.setHeader("Content-type", ContentType.JSON.toString());
            response.getOutputStream().write(JSONUtil.toJsonStr(map).getBytes(StandardCharsets.UTF_8));
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map cartPay(CartPayInfo cartPayInfo) throws Exception {
        List<Cart> cartList = cartPayInfo.getCartList();
        //先写死1， 1就是1分钱，100=1元
        Integer price = cartPayInfo.getSum();
        //商户订单号(随机字符串),这个回调的时候会给回
        String orderSn = payUtil.generateNonceStr();
        //微信用户openId
        String openid = cartList.get(0).getSellId();
        //这个是微信小程序的appid
        String appid = wxConfig.getAppID();
        //商户号ID
        String mchId = wxConfig.getMchId();
        //设置下支付的超时时间
        String timeExpire = DateTimeZoneUtil.dateToTimeZone(System.currentTimeMillis() + 1000 * 60 * 3);
        Map<String, Object> data = payUtil.requestWxPayParam(mchId,orderSn, openid, price, appid, timeExpire);
        log.info("统一下单参数 {}", JSONUtil.toJsonStr(data));
        //这个是证书文件，先写死，后续调整成读取证书文件的服务器存放地址
        String privateKeyPath = "D:\\360极速浏览器下载\\WXCertUtil\\cert\\1621751822_20230422_cert\\apiclient_key.pem";
        //请求下单
        IJPayHttpResponse response = WxPayApi.v3(
                RequestMethodEnum.POST,
                WxDomainEnum.CHINA.toString(),
                BasePayApiEnum.JS_API_PAY.toString(),
                // 商户号
                mchId,
                // 获取证书序列号
                payUtil.getSerialNumber(),
                null,
                // 私钥
                privateKeyPath,
                // 请求参数
                JSONUtil.toJsonStr(data)
        );
        log.info("统一下单响应 {}", response);
        //这个就是小程序唤醒微信支付必要的那6个参数了
        Map<String, String> map =new HashMap<>();
        if (response.getStatus() == 200) {
            //这个是证书文件，先写死，后续调整成读取证书文件的服务器存放地址
            // 根据证书序列号查询对应的证书来验证签名结果
            String platformCertPath = "D:\\360极速浏览器下载\\WXCertUtil\\cert\\1621751822_20230422_cert\\cert.pem";
            //平台证书
            boolean verifySignature = WxPayKit.verifySignature(response,platformCertPath);
            log.info("verifySignature: {}", verifySignature);
            if (verifySignature) {
                String body = response.getBody();
                JSONObject jsonObject = JSONUtil.parseObj(body);
                String prepayId = jsonObject.getStr("prepay_id");
                // 私钥
                map = WxPayKit.jsApiCreateSign(appid, prepayId,privateKeyPath);
                log.info("唤起支付参数:{}", map);
            }
        }
        String timeStamp = map.get("timeStamp");
        //todo 微信预支付的订单新入库，为了业务查询记录
        transactionTemplate.execute(status -> {
            try {
                cartList.forEach(cart -> {
                    Order order = Order.builder()
                            .orderId(orderSn)
                            .createdTime(timeStamp)
                            .buyerId(cart.getUserId())
                            .sellerId(cart.getSellId())
                            .audit(0)
                            .bookId(cart.getBookId()).build();
                    orderMapper.addOrder(order);
                    bookMapper.updateBookAudit(3,cart.getBookId());
                    cartMapper.delCart(cart.getBookId());
                });
            } catch (Exception e) {
                status.setRollbackOnly();
                throw new RuntimeException(e);
            }
            return Boolean.TRUE;
        });
        return map;
    }
}

