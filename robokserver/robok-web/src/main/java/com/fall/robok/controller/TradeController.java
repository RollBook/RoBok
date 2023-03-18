package com.fall.robok.controller;

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
 * @date 2022/9/25 16:47
 */

@RestController
@RequestMapping("/trade")
public class TradeController {

    private final OrderServiceImpl orderService;

    @Autowired
    public TradeController (OrderServiceImpl orderService){
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

}
