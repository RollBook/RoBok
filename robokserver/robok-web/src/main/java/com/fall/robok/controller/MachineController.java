package com.fall.robok.controller;

import com.fall.robok.po.Cart;
import com.fall.robok.service.IMachineService;
import com.fall.robok.service.impl.MachineServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author 楠檀,
 * @date 2023/5/25,
 * @time 16:23,
 */

@Validated
@RestController
@RequestMapping("/machine")
public class MachineController {

    private final MachineServiceImpl machineService;

    private final RedisTemplate<Object,Object> redisTemplate;

    public MachineController(MachineServiceImpl machineService,RedisTemplate<Object,Object> redisTemplate){
        this.machineService = machineService;
        this.redisTemplate = redisTemplate;
    }

    @ApiOperation("灯光控制")
    @PostMapping("/light_control")
    public ResBean lightControl(@RequestParam("mes") String mes){
        String ret = machineService.lightControl(mes);
        return ResBean.ok("ok",ret);
    }

    @ApiOperation("门锁控制")
    @PostMapping("/lock_control")
    public ResBean lockControl(@RequestParam("mes") String mes){
        String ret = machineService.lockControl(mes);
        return ResBean.ok("ok",ret);
    }

    @ApiOperation("蜂鸣器控制")
    @PostMapping("/beep_control")
    public ResBean beepControl(@RequestParam("mes") String mes){
        String ret = machineService.beepControl(mes);
        return ResBean.ok("ok",ret);
    }

    @ApiOperation("获取环境")
    @GetMapping("/getSubscribe")
    public ResBean getSubscribe(@RequestParam("topic")String topic) {
        return ResBean.ok("ok",redisTemplate.opsForValue().get(topic));
    }

    @ApiOperation("机器强制控制")
    @PostMapping("/machine_control")
    public ResBean machineControl(@RequestParam("mes") String mes){
        String ret = machineService.machineControl(mes);
        return ResBean.ok("ok",ret);
    }
}
