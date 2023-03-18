package com.fall.robok.service.impl;

import com.fall.robok.mapper.OrderMapper;
import com.fall.robok.po.Book;
import com.fall.robok.po.Order;
import com.fall.robok.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/3/9,
 * @time 15:33,
 */
@Service
public class OrderServiceImpl implements IOrderService {

    private final OrderMapper orderMapper;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
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
}
