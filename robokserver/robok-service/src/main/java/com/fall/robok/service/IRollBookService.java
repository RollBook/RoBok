package com.fall.robok.service;

import com.fall.robok.po.Book;
import com.fall.robok.po.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * @author FAll
 * @date 2022/11/21 10:28
 */
public interface IRollBookService {

    /**
     * @author FAll
     * @description 获取首页轮播图
     * @return: java.util.ArrayList<java.lang.String>
     * @date 2022/11/21 10:29
     */
    ArrayList<String> getAllIndexSwiper();

    List<Book> getSaveBook(String openid);

    Integer saveBook(String bookId);

    List<Order> getPickOrder(String openid);

    Integer pickBook(String orderId);
}
