package com.fall.robok.task;

import com.alibaba.fastjson.JSON;
import com.fall.robok.Config.WxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author FAll
 * @date 2022/9/25 17:17
 */

@Component
public class WXTask {

    @Autowired
    WxConfig wxConfig;

    public static String accessToken;

    /**
     * @param
     * @author FAll
     * @description 获取微信官方下发的AccessToken, 每两小时更新一次
     * @date 2022/9/25 17:30
     */
    @PostConstruct
    @Scheduled(cron = "0 0 0/2 * * ?")
    public void getAcessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=" +
                "client_credential&appid=" + wxConfig.getAppID() +
                "&secret=" + wxConfig.getAppSecret();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Connection", "close");
        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<String> response = new RestTemplate().exchange(url, HttpMethod.GET, requestEntity, String.class);

        Map ret = (Map) JSON.parseObject(response.getBody());
        accessToken = (String) ret.get("access_token");

    }


}
