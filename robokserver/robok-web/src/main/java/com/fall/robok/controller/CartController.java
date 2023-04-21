package com.fall.robok.controller;

import com.fall.robok.po.Cart;
import com.fall.robok.service.impl.CartServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author 楠檀,
 * @date 2023/4/19,
 * @time 10:40,
 */

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartServiceImpl cartService;

    public CartController(CartServiceImpl cartService){
        this.cartService = cartService;
    }

    /**
     * @author Tan
     * @description 获取购物车
     * @param userId
     * @return: com.fall.robok.util.bean.ResBean
     * @date  10:44
     */

    @ApiOperation("获取购物车")
    @GetMapping("/get_cart")
    public ResBean getCart(@NotEmpty @RequestParam("userId") String userId){
        List<Cart> cartList = cartService.getCart(userId);
        return ResBean.ok("ok",cartList);
    }

    @ApiOperation("删除购物车书本")
    @DeleteMapping("/del_cart")
    public ResBean delCart(@NotEmpty @RequestParam("bookId") String bookId){
        Integer k = cartService.delCart(bookId);
        return ResBean.ok("ok",k);
    }
}
