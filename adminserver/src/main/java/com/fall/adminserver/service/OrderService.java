package com.fall.adminserver.service;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.Order;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.models.security.SecurityScheme;

/**
 * @author 楠檀,
 * @date 2023/5/11,
 * @time 14:37,
 */
public interface OrderService {

    PageInfo<Order> getOrder(Integer pageNum, Integer pageSize, String order, String orderProp);

    PageInfo<Order> getOrderByName(String bookName,Integer pageNum, Integer pageSize, String order, String orderProp);

    Integer delOrder(String orderId);

    PageInfo<Order> getRecycleOrder(Integer pageNum, Integer pageSize, String order, String orderProp);

    PageInfo<Order> getRecycleOrderByName(String bookName,Integer pageNum, Integer pageSize, String order, String orderProp);

    Integer recycleOrder(String orderId);
}
