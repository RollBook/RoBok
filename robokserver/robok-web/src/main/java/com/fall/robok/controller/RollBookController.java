package com.fall.robok.controller;

import com.fall.robok.gateway.MqttGateway;
import com.fall.robok.po.Book;
import com.fall.robok.po.Order;
import com.fall.robok.service.impl.RollBookServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author FAll
 * @date 2022/9/24 14:37
 */

@Validated
@RestController
@RequestMapping("/rollbook")
public class RollBookController {

    private final RollBookServiceImpl rollBookService;

    private final MqttGateway gateway;

    public RollBookController(RollBookServiceImpl rollBookService,MqttGateway gateway){
        this.rollBookService = rollBookService;
        this.gateway = gateway;
    }

    private boolean pos;

    /**
     * @author FAll
     * @description 获取首页轮播图
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/24 15:45
     */
    @ApiOperation("获取小程序首页轮播图")
    @GetMapping("/get_index_swiper")
    public ResBean getIndexSwiper() {
        return ResBean.ok("ok", rollBookService.getAllIndexSwiper());
    }


//    @GetMapping("/roll")
//    public ResBean roll() {
//        if(pos){
//            gateway.sendToMqtt("1","RoServe/usr/roll");
//        } else {
//            gateway.sendToMqtt("2","RoServe/usr/roll");
//        }
//        pos = !pos;
//        return ResBean.ok("ok");
//    }

    @ApiOperation("获取待存入书本")
    @GetMapping("get_save_book")
    public ResBean getSaveBook(@Param("openid") String openid){
        List<Book> saveBook = rollBookService.getSaveBook(openid);
        return ResBean.ok("ok",saveBook);
    }

    @ApiOperation("存入书本")
    @PostMapping ("/save_book")
    public ResBean saveBook(@RequestParam("bookId") String bookId){
        Integer au = rollBookService.saveBook(bookId);
        return ResBean.ok("ok",au);
    }


    @ApiOperation("获取待取出书本")
    @GetMapping("get_pick_order")
    public ResBean getPickOrder(@Param("openid") String openid){
        List<Order> pickOrder = rollBookService.getPickOrder(openid);
        return ResBean.ok("ok",pickOrder);
    }


    @ApiOperation("取出书本")
    @PostMapping ("/pick_order")
    public ResBean pickOrder(@RequestParam("orderId") String orderId){
        Integer au = rollBookService.pickBook(orderId);
        return ResBean.ok("ok",au);
    }


}
