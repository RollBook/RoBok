package com.fall.robok.service;

import com.fall.robok.mapper.MallMapper;
import com.fall.robok.po.Book;
import com.fall.robok.po.Cart;
import com.fall.robok.po.MallSwiper;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/13,
 * @time 17:25,
 */
public interface IMallService {

    /**
     * @author Tan
     * @description 商城轮播图获取
     * @param
     * @return: java.util.List<com.fall.robok.mapper.MallMapper>
     * @date  17:41
     */
    List<MallSwiper> getMallSwiper();

    List<Book> getGoods(Integer nowPage);

    List<Book> searchBooks(String input);

    List<Book> searchBooksByHow(String input);

    List<Book> searchBooksByPrice(String input);

    Book getGoodById(String bookId);

    Integer addCart(Cart cart);
}
