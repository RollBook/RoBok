package com.fall.robok.controller;

import com.fall.robok.gateway.MqttGateway;
import com.fall.robok.service.impl.RollBookServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



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


    @GetMapping("/roll")
    public ResBean roll() {
        if(pos){
            gateway.sendToMqtt("1","RoServe/usr/roll");
        } else {
            gateway.sendToMqtt("2","RoServe/usr/roll");
        }
        pos = !pos;
        return ResBean.ok("ok");
    }

}
