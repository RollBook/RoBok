package com.fall.robok.service;

import com.fall.robok.po.Cart;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/19,
 * @time 10:36,
 */
public interface ICartService {

    List<Cart> getCart(String userId);

    Integer delCart(String bookId);
}
