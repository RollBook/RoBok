package com.fall.robok.controller;

import com.fall.robok.po.Book;
import com.fall.robok.po.Order;
import com.fall.robok.service.impl.OrderServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author FAll
 * @date 2023/3/18 18:37
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderServiceImpl orderService;

    @Autowired
    public OrderController (OrderServiceImpl orderService){
        this.orderService = orderService;
    }

    /**
     * @author Tan
     * @description 买书书架，获取书本
     * @param openid openid
     * @return: com.fall.robok.util.bean.ResBean
     * @date  15:51
     */
    @ApiOperation("买书书架，获取书本")
    @GetMapping("/get_order")
    public ResBean getOrder(@NotEmpty @RequestParam("openid") String openid){
        List<Order> order = orderService.getOrder(openid);
        return ResBean.ok("ok",order);
    }

    /**
     * @author Tan
     * @description 卖书书架，获取书本
     * @param openid openid
     * @return: com.fall.robok.util.bean.ResBean
     * @date  15:20
     */
    @ApiOperation("卖书书架，获取书本")
    @GetMapping("/get_sell_book")
    public ResBean getSellBook(@NotEmpty @RequestParam("openid") String openid){
        List<Book> books = orderService.getSellBook(openid);
        return ResBean.ok("ok",books);
    }
}
