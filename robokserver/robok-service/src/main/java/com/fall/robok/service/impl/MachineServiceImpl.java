package com.fall.robok.service.impl;

import com.fall.robok.gateway.MqttGateway;
import com.fall.robok.service.IMachineService;
import org.springframework.stereotype.Service;

/**
 * @author 楠檀,
 * @date 2023/5/25,
 * @time 16:25,
 */

@Service
public class MachineServiceImpl implements IMachineService {

    private final MqttGateway mqttGateway;

    public MachineServiceImpl(MqttGateway mqttGateway){
        this.mqttGateway = mqttGateway;
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

    @Override
    public String machineControl(String mes) {
        mqttGateway.sendToMqtt(mes,"RoServe/usr/roll");
        return mes;
    }
}
