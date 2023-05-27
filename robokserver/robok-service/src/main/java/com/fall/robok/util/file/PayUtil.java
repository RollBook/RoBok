package com.fall.robok.util.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fall.robok.config.ServerConfig;
import com.ijpay.core.kit.AesUtil;
import com.ijpay.core.kit.PayKit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 楠檀,
 * @date 2023/4/23,
 * @time 14:05,
 */

@Slf4j
@Component
@Getter
public class PayUtil {

    private final ServerConfig serverConfig;

    public PayUtil(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }

    @Value("${certificate-path.certPath}")
    private String certPath;

    @Value("${certificate-path.privateKeyPath}")
    private String privateKeyPath;

    @Value("${certificate-path.privateCertPath}")
    private String privateCertPath;


    /**
     * 保存订单的支付通知明文
     *
     * @param plainText
     */
    public void savePayPlainText(String plainText) {
        JSONObject jsonObject = JSONUtil.parseObj(plainText);
        //这个就是发起订单时的那个订单号
        String outTradeNo = jsonObject.getStr("out_trade_no");
        //todo 把微信支付回调的明文消息存进数据库，方便后续校验查看

        //todo 把微信支付后需要处理的具体业务处理了
    }


    /**
     * 直连支付组装参数
     *
     */
    public Map<String, Object> requestWxPayParam(String mchId, String orderSn, String openid, Integer price, String appid, String timeExpire) {

        Map<String, Object> data = new HashMap<String, Object>();
        Map<String, String> user = new HashMap<>();
        user.put("openid", openid);
        Map<String, Object> fee = new HashMap<>();
        fee.put("total", price);
        //APPID
        data.put("appid", appid);
        //商户ID
        data.put("mchid", mchId);
        //生成的随机字符串
        data.put("description", "微信支付");
        data.put("out_trade_no", orderSn);
        //支付金额，单位分,1是1分钱
        data.put("amount", fee);
        data.put("time_expire", timeExpire);
        //通知地址，用户支付成功之后，微信访问的接口
        data.put("notify_url", "http://"+serverConfig.getIp()+":"
                +serverConfig.getPort()+"/api/order/payNotify");
        data.put("payer", user);
        return data;
    }

    /**
     * 获取证书序列号
     *
     */
    public String getSerialNumber() throws IOException {
        //这个是证书文件，先写死，后续调整成读取证书文件的服务器存放地址
        String certPath = this.privateCertPath;
        log.info("path:{}", certPath);
        // 获取证书序列号
        FileInputStream fileInputStream = new FileInputStream(certPath);
        X509Certificate certificate = PayKit.getCertificate(fileInputStream);
        String serialNo = certificate.getSerialNumber().toString(16).toUpperCase();
        log.info("获取证书序列号：{},", serialNo);
        fileInputStream.close();
        return serialNo;
    }

    /**
     * 获取随机字符串 Nonce Str
     *
     * @return String 随机字符串
     */
    public String generateNonceStr() {
        StringBuffer stringBuffer = new StringBuffer();
        int prefix = RandomUtil.randomInt(10000, 99999);
        int suffix = RandomUtil.randomInt(10000, 99999);
        Long time = System.currentTimeMillis();
        return stringBuffer.append(prefix).append(time).append(suffix).toString();
    }

    public String savePlatformCert(String associatedData, String apiKey3, String nonce, String cipherText, String certPath) {
        try {
            AesUtil aesUtil = new AesUtil(apiKey3.getBytes(StandardCharsets.UTF_8));
            // 平台证书密文解密
            // encrypt_certificate 中的  associated_data nonce  ciphertext
            String publicKey = aesUtil.decryptToString(
                    associatedData.getBytes(StandardCharsets.UTF_8),
                    nonce.getBytes(StandardCharsets.UTF_8),
                    cipherText
            );
            log.info("获取证书key：{},保存路径platformCert:{}", publicKey, certPath);
            //将生成的证书写入指定路径，文件名为：cert.pem
            FileOutputStream fos = new FileOutputStream(certPath);
            fos.write(publicKey.getBytes());
            fos.close();

            // 获取平台证书序列号
            X509Certificate certificate = PayKit.getCertificate(new ByteArrayInputStream(publicKey.getBytes()));
            return certificate.getSerialNumber().toString(16).toUpperCase();
        } catch (Exception e) {
            log.error("写入证书错误:{}", e);
            return e.getMessage();
        }
    }
}
