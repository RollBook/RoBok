package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.OrderMapper;
import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.Order;
import com.fall.adminserver.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 楠檀,
 * @date 2023/5/11,
 * @time 14:37,
 */

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderMapper orderMapper){
        this.orderMapper = orderMapper;
    }
    @Override
    public PageInfo<Order> getOrder(Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Order> orderList = orderMapper.getOrder(null, order, orderProp);
        List<Order> toRemove = new ArrayList<>();
        for(Order o : orderList) {
            if(o.getAudit() == 9) {
                toRemove.add(o);
            }
        }
        orderList.removeAll(toRemove);
        return new PageInfo<>(orderList,5);
    }

    @Override
    public PageInfo<Order> getOrderByName(String bookName, Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Order> orderList = orderMapper.getOrder('%'+bookName+'%', order, orderProp);
        List<Order> toRemove = new ArrayList<>();
        for(Order o : orderList) {
            if(o.getAudit() == 9) {
                toRemove.add(o);
            }
        }
        orderList.removeAll(toRemove);
        return new PageInfo<>(orderList,5);
    }

    @Override
    public Integer delOrder(String orderId) {
        return orderMapper.delOrder(orderId);
    }

    @Override
    public PageInfo<Order> getRecycleOrder(Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Order> orderList = orderMapper.getOrder(null, order, orderProp);
        List<Order> toRemove = new ArrayList<>();
        for(Order o : orderList) {
            if(o.getAudit() != 9) {
                toRemove.add(o);
            }
        }
        orderList.removeAll(toRemove);
        return new PageInfo<>(orderList,5);
    }

    @Override
    public PageInfo<Order> getRecycleOrderByName(String bookName, Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<Order> orderList = orderMapper.getOrder('%'+bookName+'%', order, orderProp);
        List<Order> toRemove = new ArrayList<>();
        for(Order o : orderList) {
            if(o.getAudit() == 9) {
                toRemove.add(o);
            }
        }
        orderList.removeAll(toRemove);
        return new PageInfo<>(orderList,5);
    }

    @Override
    public Integer recycleOrder(String orderId) {
        return orderMapper.recycleOrder(orderId);
    }
}
