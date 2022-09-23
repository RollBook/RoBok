package com.fall.robok;

import com.alibaba.fastjson.JSON;
import com.fall.robok.Config.WxConfig;
import com.fall.robok.mapper.UserMapper;
import com.fall.robok.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FAll
 * @date 2022/9/22 23:47
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    WxConfig wxConfig;

    /**
     * @param
     * @author FAll
     * @description 获取所有用户信息
     * @return: java.util.List<com.fall.robok.model.User>
     * @date 2022/9/23 12:50
     */
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    public Map SignInAndSignUp(String code, String nickName) throws Exception {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="
                + wxConfig.appID + "&secret="
                + wxConfig.appSecret + "&js_code="
                + code + "&grant_type=authorization_code";

        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Connection", "close");
        HttpEntity requestEntity = new HttpEntity(requestHeaders);

        HttpEntity<String> response = template.exchange(
                url,
                HttpMethod.GET,
                requestEntity,
                String.class
        );

        Map ret = (Map) JSON.parseObject(response.getBody());
        // System.out.println(ret.get("session_key"));
        // if (ret.get("errcode") != null) {
        //     return null;
        // }
        return ret;

    }

}
