package com.fall.adminserver.config;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;

/**
 * @author FAll
 * @date 2023/3/15 18:49
 */
@Configuration
@IntegrationComponentScan
public class MqttConfig {

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.url}")
    private String hostUrl;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.default.topic}")
    private String defaultTopic;

    private final RedisTemplate<Object,Object> redisTemplate;

    public MqttConfig(RedisTemplate<Object,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }


    private final String[]  SUBSCRIBE_TOPIC_LIST = {
            "RoServe/usr/envir",
            "RoServe/usr/equip",
            "RoServe/usr/lock",
            "RoServe/usr/beep",
            "RoServe/usr/light"};

//    @Value("${spring.mqtt.publish}")
//    private String[] publishTopicList;

    @Bean
    public MqttConnectOptions getMqttConnectOptions(){
        MqttConnectOptions mqttConnectOptions=new MqttConnectOptions();
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());
        mqttConnectOptions.setServerURIs(new String[]{hostUrl});
        mqttConnectOptions.setKeepAliveInterval(2);
        return mqttConnectOptions;
    }
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(getMqttConnectOptions());
        return factory;
    }
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MqttPahoMessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =  new MqttPahoMessageHandler(clientId, mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(defaultTopic);
        return messageHandler;
    }
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }


    @Bean
    public MemoryPersistence memoryPersistence() {
        return new MemoryPersistence();
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        MqttClient mqttClient = new MqttClient(hostUrl, clientId, memoryPersistence());
        mqttClient.setCallback(new MqttCallback() {

            public void connectionLost(Throwable cause) {
                System.out.println("connectionLost");
            }

            public void messageArrived(String topic, MqttMessage message) throws Exception {
                redisTemplate.opsForValue().set(topic,new String(message.getPayload()));
                System.out.println("topic:" + topic);
                System.out.println("Qos:" + message.getQos());
                System.out.println("message content:" + new String(message.getPayload()));

            }

            public void deliveryComplete(IMqttDeliveryToken token) {
                System.out.println("deliveryComplete---------" + token.isComplete());
            }

        });

        mqttClient.connect(getMqttConnectOptions());
        mqttClient.subscribe(SUBSCRIBE_TOPIC_LIST);

        return mqttClient;
    }


}
