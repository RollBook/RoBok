package com.fall.robok.mapper;

import com.fall.robok.po.Cart;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/19,
 * @time 10:31,
 */

@Mapper
@Repository
public interface CartMapper {

    List<Cart> getCart(String userId);

    Integer delCart(String bookId);
}
