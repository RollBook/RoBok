package com.fall.robok.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fall.robok.config.WxConfig;
import com.fall.robok.mapper.UserMapper;
import com.fall.robok.model.User;
import com.fall.robok.service.IUserService;
import com.fall.robok.task.WXTask;
import com.fall.robok.util.encrypt.SHA256Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author FAll
 * @date 2022/9/22 23:47
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WxConfig wxConfig;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @param
     * @author FAll
     * @description 获取所有用户信息
     * @return: java.util.List<com.fall.robok.model.User>
     * @date 2022/9/23 12:50
     */
    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    /**
     * @param code
     * @param nickName
     * @author FAll
     * @description 用户登录&用户注册
     * @return: java.util.Map
     * @date 2022/9/23 14:50
     */
    @Override
    public Map SignInAndSignUp(String code, String nickName) throws Exception {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxConfig.getAppID() + "&secret=" + wxConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";

        // 发送token到微信服务器，兑换session
        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Connection", "close");
        HttpEntity requestEntity = new HttpEntity(requestHeaders);
        HttpEntity<String> response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        Map ret = (Map) JSON.parseObject(response.getBody());
        if (ret.get("errcode") != null) {
            return null;
        }

        // 根据response查询用户是否存在
        String openId = (String) ret.get("openid");
        String session = (String) ret.get("session_key");
        HashMap<String, String> returnMap = new HashMap<>();
        Integer isUserExist = userMapper.userIsExist(openId);

        /*  如果用户存在, 直接更新session存入redis，并为用户发放新的session_key(SHA256)
            如果不存在，先走注册流程，再更新session */

        if (isUserExist == null) {  // 注册
            String createdTime = String.valueOf(System.currentTimeMillis());
            userMapper.userSignUp(openId, nickName, createdTime);
        }

        // 生成session_key，并发给用户
        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        stringValueOperations.set(openId, session, 1, TimeUnit.DAYS);

        returnMap.put("openid", openId);
        returnMap.put("session_key", SHA256Encrypt.getSHA256Str(session));
        return returnMap;

    }

    /**
     * @param openId
     * @param sessionKey
     * @author FAll
     * @description 检查用户登录信息是否过期
     * @return: java.lang.Boolean
     * @date 2022/9/24 15:21
     */
    @Override
    public Boolean isLogin(String openId, String sessionKey) {

        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();
        if (openId == null) {
            return null;
        }
        String session = stringValueOperations.get(openId);

        Boolean isLogin = null;
        if (session == null) { // 已过期或未注册
            isLogin = false;
        } else if (SHA256Encrypt.getSHA256Str(session).equals(sessionKey)) { // 尚未过期
            isLogin = true;
        } else { // 恶意请求
            isLogin = null;
        }


        return isLogin;
    }

    /**
     * @param code
     * @param openId
     * @author FAll
     * @description 获取用户手机号
     * @return: java.lang.String
     * @date 2022/9/25 18:12
     */
    @Override
    public String getPhoneNum(String code, String openId) {
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + WXTask.accessToken;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        requestHeaders.set("Connection", "close");
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());

        JSONObject requestMap = new JSONObject();
        requestMap.put("code", code);
        HttpEntity<JSONObject> entity = new HttpEntity<>(requestMap, requestHeaders);
        JSONObject res = new RestTemplate().postForObject(url, entity, JSONObject.class);

        if ((Integer) res.get("errcode") != 0) {
            return null;
        }
        Map<String, String> phoneInfo = (Map<String, String>) res.get("phone_info");
        String phone = "+"
                + phoneInfo.get("countryCode")
                + " "
                + phoneInfo.get("purePhoneNumber");
        if (userMapper.updateByOpenId(new User(
                openId,
                null,
                null,
                phone,
                null)) == 0) {
            return null;
        }

        return phone;

    }

}
