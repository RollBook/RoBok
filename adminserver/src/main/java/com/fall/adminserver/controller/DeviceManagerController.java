package com.fall.adminserver.controller;

import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author FAll
 * @date 2023/4/13 下午2:43
 * 管理设备
 */
@RestController
@RequestMapping("/device")
public class DeviceManagerController {

    private final DeviceService deviceService;

    private final RedisTemplate<Object,Object> redisTemplate;

    public DeviceManagerController(DeviceService deviceService,RedisTemplate<Object,Object> redisTemplate){
        this.deviceService = deviceService;
        this.redisTemplate = redisTemplate;
    }

    @Operation(summary = "控制机器")
    @PostMapping("/machine_control")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<String> machineDevice(@RequestParam String pressId){
        return Optional.ofNullable(deviceService.machineDevice(pressId))
                .map(e->(ResponseRecord.success("启动成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "灯光控制")
    @PostMapping("/light_control")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<String> lightControl(@RequestParam("mes") String mes){

        return Optional.ofNullable(deviceService.lightControl(mes))
                .map(e->(ResponseRecord.success("启动成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "门锁控制")
    @PostMapping("/lock_control")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<String> lockControl(@RequestParam("mes") String mes){

        return Optional.ofNullable(deviceService.lockControl(mes))
                .map(e->(ResponseRecord.success("启动成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "蜂鸣器控制")
    @PostMapping("/beep_control")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<String> beepControl(@RequestParam("mes") String mes){

        return Optional.ofNullable(deviceService.beepControl(mes))
                .map(e->(ResponseRecord.success("启动成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "获取环境")
    @GetMapping("/get_subscribe")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<String> getSubscribe(@RequestParam("topic")String topic){
        Object subscribe = redisTemplate.opsForValue().get(topic);
        return Optional.ofNullable(subscribe.toString())
                .map(ResponseRecord::success)
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

}
