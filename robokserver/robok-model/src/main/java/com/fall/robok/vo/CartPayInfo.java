package com.fall.robok.vo;

import com.fall.robok.po.Cart;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/24,
 * @time 9:00,
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartPayInfo {

    private Integer sum;

    private List<Cart> cartList;
}
