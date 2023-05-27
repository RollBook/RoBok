package com.fall.adminserver.gateway;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

/**
 * @author FAll
 * @date 2023/3/15 18:57
 */
@Component
public class MqttGateway {

    private final MqttClient client;

    public MqttGateway(MqttClient client){
        this.client = client;
    }

    public void sendToMqtt(String data, String topic){
        try {
            client.publish(topic,new MqttMessage(data.getBytes()));
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

}
