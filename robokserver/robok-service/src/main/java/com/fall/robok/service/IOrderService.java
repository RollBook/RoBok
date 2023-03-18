package com.fall.robok.service;

import com.fall.robok.po.Book;
import com.fall.robok.po.Order;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/3/9,
 * @time 15:32,
 */
public interface IOrderService {

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
}
