package com.fall.robok.service.impl;

import com.fall.robok.mapper.CartMapper;
import com.fall.robok.po.Cart;
import com.fall.robok.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/19,
 * @time 10:36,
 */

@Service
public class CartServiceImpl implements ICartService {

    private final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMapper){
        this.cartMapper = cartMapper;
    }


    @Override
    public List<Cart> getCart(String userId) {
        return cartMapper.getCart(userId);
    }

    @Override
    public Integer delCart(String bookId) {
        return cartMapper.delCart(bookId);
    }
}
