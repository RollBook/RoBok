package com.fall.robok.service.impl;

import com.fall.robok.config.ServerConfig;
import com.fall.robok.mapper.OrderMapper;
import com.fall.robok.model.Book;
import com.fall.robok.model.Order;
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

    private final ServerConfig serverConfig;

    @Autowired
    public OrderServiceImpl(OrderMapper orderMapper,ServerConfig serverConfig){
        this.orderMapper = orderMapper;
        this.serverConfig = serverConfig;
    }

    @Override
    public List<Order> getOrder(String openid){
        List<Order> order = orderMapper.getOrder(openid);
        return order;
    }
}
