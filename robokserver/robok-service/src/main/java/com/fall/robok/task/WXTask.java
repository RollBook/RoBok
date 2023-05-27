package com.fall.robok.task;

import com.fall.robok.config.WxConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author FAll
 * @date 2022/9/25 17:17
 */

@Component
public class WXTask {

    private final WxConfig wxConfig;

    private final ObjectMapper mapper;

    @Autowired
    public WXTask(WxConfig wxConfig,ObjectMapper mapper,MqttClient mqttClient){
        this.wxConfig = wxConfig;
        this.mapper=mapper;
    }

    public static String accessToken;

    /**
     * @author FAll
     * @description 获取微信官方下发的AccessToken, 每两小时更新一次
     * @date 2022/9/25 17:30
     */
    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void getAcessToken() throws MqttException {

        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" +
                "client_credential&appid=" + wxConfig.getAppID() +
                "&secret=" + wxConfig.getAppSecret();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Connection", "close");
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(requestHeaders);
        HttpEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);

        HashMap ret = null;
        try {
            ret = mapper.readValue(response.getBody(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        accessToken = (String) ret.get("access_token");

    }


}
