package com.fall.robok.mapper;

import com.fall.robok.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/3/9,
 * @time 15:25,
 */

@Mapper
@Repository
public interface OrderMapper {

    /**
     * @author Tan
     * @description
     * @param openid openid
     * @return: java.util.List<com.fall.robok.model.Order>
     * @date  15:28
     */
    List<Order> getOrder(@Param("openid") String openid);
}
