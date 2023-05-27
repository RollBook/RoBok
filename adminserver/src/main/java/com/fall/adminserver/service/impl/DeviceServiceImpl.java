package com.fall.adminserver.service.impl;

import com.fall.adminserver.gateway.MqttGateway;
import com.fall.adminserver.service.DeviceService;
import org.springframework.stereotype.Service;

/**
 * @author 楠檀,
 * @date 2023/5/25,
 * @time 14:04,
 */

@Service
public class DeviceServiceImpl implements DeviceService {

    private final MqttGateway mqttGateway;

    public DeviceServiceImpl(MqttGateway mqttGateway){
        this.mqttGateway = mqttGateway;
    }

    @Override
    public String machineDevice(String pressId) {
        mqttGateway.sendToMqtt(pressId,"RoServe/usr/roll");
        return pressId;
    }

    @Override
    public String lightControl(String mes) {
        mqttGateway.sendToMqtt(mes,"RoServe/usr/light1");
        return mes;
    }

    @Override
    public String lockControl(String mes) {
        mqttGateway.sendToMqtt(mes,"RoServe/usr/lock1");
        return mes;
    }

    @Override
    public String beepControl(String mes) {
        mqttGateway.sendToMqtt(mes,"RoServe/usr/beep1");
        return mes;
    }
}
