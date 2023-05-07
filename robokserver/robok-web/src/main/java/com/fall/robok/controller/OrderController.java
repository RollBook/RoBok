package com.fall.robok.controller;

import com.fall.robok.po.Book;
import com.fall.robok.po.Cart;
import com.fall.robok.po.Order;
import com.fall.robok.service.impl.OrderServiceImpl;
import com.fall.robok.util.bean.ResBean;
import com.fall.robok.vo.CartPayInfo;
import com.fall.robok.vo.OrderOfPay;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author FAll
 * @date 2023/3/18 18:37
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController (OrderServiceImpl orderService){
        this.orderService = orderService;
    }

    /**
     * @author Tan
     * @description 买书书架，获取书本
     * @param openid openid
     * @return: com.fall.robok.util.bean.ResBean
     * @date  15:51
     */
    @ApiOperation("买书书架，获取书本")
    @GetMapping("/get_order")
    public ResBean getOrder(@NotEmpty @RequestParam("openid") String openid){
        List<Order> order = orderService.getOrder(openid);
        return ResBean.ok("ok",order);
    }

    /**
     * @author Tan
     * @description 卖书书架，获取书本
     * @param openid openid
     * @return: com.fall.robok.util.bean.ResBean
     * @date  15:20
     */
    @ApiOperation("卖书书架，获取书本")
    @GetMapping("/get_sell_book")
    public ResBean getSellBook(@NotEmpty @RequestParam("openid") String openid){
        List<Book> books = orderService.getSellBook(openid);
        return ResBean.ok("ok",books);
    }


    /**
     * @author Tan
     * @description 微信统一下单接口
     * @param orderOfPay
     * @return: com.fall.robok.util.bean.ResBean
     * @date  14:57
     */

    @ApiOperation("微信统一下单接口")
    @PostMapping("/doUnifiedOrder")
    public ResBean doUnifiedOrder(@RequestBody OrderOfPay orderOfPay) throws Exception {
        Map map = orderService.doUnifiedOrder(orderOfPay);
        return ResBean.ok("ok",map);
    }


    /**
     * @author Tan
     * @description 微信支付的回调接收接口
     * @param request
     * @param response
     * @date  14:56
     */
    @RequestMapping(value = "/payNotify", method = {org.springframework.web.bind.annotation.RequestMethod.POST, org.springframework.web.bind.annotation.RequestMethod.GET})
    public ResBean callBack(HttpServletRequest request, HttpServletResponse response) {
        orderService.callBack(request, response);
        return ResBean.ok("ok");
    }

    /**
     * 生成v3证书
     *
     */
    @RequestMapping("/createPlatformCert")
    @ResponseBody
    public String createPlatformCert() throws IOException {
        return orderService.createPlatformCert();
    }


    @PostMapping("/cartPay")
    public ResBean cartPay(@RequestBody CartPayInfo cartPayInfo) throws Exception {
        System.out.println(cartPayInfo);
        Map map = orderService.cartPay(cartPayInfo);
        return ResBean.ok("ok",map);
    }

}
