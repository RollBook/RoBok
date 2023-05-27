package com.fall.adminserver.mapper;

import com.fall.adminserver.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/5/11,
 * @time 11:03,
 */

@Mapper
@Repository
public interface OrderMapper {

    List<Order> getOrder(String bookName,String order, String orderProp);

    Integer delOrder(String orderId);

    Integer recycleOrder(String orderId);
}
