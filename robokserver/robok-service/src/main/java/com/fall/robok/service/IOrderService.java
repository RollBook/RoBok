package com.fall.robok.service;

import com.fall.robok.po.Book;
import com.fall.robok.po.Order;
import com.fall.robok.vo.CartPayInfo;
import com.fall.robok.vo.OrderOfPay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author 楠檀,
 * @date 2023/3/9,
 * @time 15:32,
 */
public interface IOrderService<T> {

    /**
     * @author Tan
     * @description
     * @param openid openid
     * @return: java.util.List<com.fall.robok.po.Order>
     * @date  15:34
     */
    List<Order> getOrder(String openid);

    /**
     * @author Tan
     * @description 卖书书架，获取书本
     * @param openid openid
     * @return: java.util.List<com.fall.robok.po.Book>
     * @date  15:33
     */
    List<Book> getSellBook(String openid);

    /**
     * 微信统一下单接口
     *
     * @param iWxPayParamVO
     * @return
     * @throws Exception
     */
    Map doUnifiedOrder(OrderOfPay orderOfPay) throws Exception;

    /**
     * 获取v3的证书
     *
     * @return
     * @throws IOException
     */
    String createPlatformCert() throws IOException;

    /**
     * 微信支付回调接口
     *
     * @param request
     * @param response
     */
    void callBack(HttpServletRequest request, HttpServletResponse response);


    Map cartPay(CartPayInfo cartPayInfo) throws Exception;
}
