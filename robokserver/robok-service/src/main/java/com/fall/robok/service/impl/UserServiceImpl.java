package com.fall.robok.service.impl;

import com.fall.robok.config.WxConfig;
import com.fall.robok.mapper.UserMapper;
import com.fall.robok.po.User;
import com.fall.robok.service.IUserService;
import com.fall.robok.task.WXTask;
import com.fall.robok.util.encrypt.SHA256Encrypt;
import com.fall.robok.vo.UserBasicInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author FAll
 * @date 2022/9/22 23:47
 */
@Service
public class UserServiceImpl implements IUserService {

    private final UserMapper userMapper;

    private final WxConfig wxConfig;

    private final StringRedisTemplate stringRedisTemplate;

    private final ObjectMapper mapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, WxConfig wxConfig,
                           StringRedisTemplate stringRedisTemplate, ObjectMapper mapper) {
        this.userMapper = userMapper;
        this.wxConfig = wxConfig;
        this.stringRedisTemplate = stringRedisTemplate;
        this.mapper = mapper;
    }

    /**
     * @author FAll
     * @description 获取所有用户信息
     * @return: java.util.List<com.fall.robok.po.User>
     * @date 2022/9/23 12:50
     */
    @Override
    public List<User> getAllUser() {
        return userMapper.getAllUser();
    }

    /**
     * @param code     微信code
     * @param nickName 用户昵称
     * @author FAll
     * @description 用户登录&用户注册
     * @return: java.util.HashMap<java.lang.String, java.lang.String>
     * @date 2022/9/23 14:50
     */
    @Override
    public HashMap<String, String> SignInAndSignUp(String code, String nickName) throws Exception {

        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxConfig.getAppID() + "&secret=" + wxConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";

        // 发送token到微信服务器，兑换session
        RestTemplate template = new RestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Connection", "close");
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(requestHeaders);
        HttpEntity<String> response = template.exchange(url, HttpMethod.GET, requestEntity, String.class);
        HashMap<?,?> ret = mapper.readValue(response.getBody(), HashMap.class);

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
     * @param openId     openid
     * @param sessionKey 会话密钥
     * @author FAll
     * @description 检查用户登录信息是否过期
     * @return: java.lang.Boolean
     * @date 2022/9/24 15:21
     */
    @Override
    public Boolean isLogin(String openId, String sessionKey) {
        if (openId == null) {
            return null;
        }

        ValueOperations<String, String> stringValueOperations = stringRedisTemplate.opsForValue();

        String session = stringValueOperations.get(openId);

        if(session == null) {
            return false;
        } else {
            return SHA256Encrypt.getSHA256Str(session).equals(sessionKey);
        }
    }

    /**
     * @param code   微信code
     * @param openId openid
     * @author FAll
     * @description 获取用户手机号
     * @return: java.lang.String
     * @date 2022/9/25 18:12
     */
    @Override
    public String getPhoneNum(String code, String openId) {

        // 初始化请求信息
        String url = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + WXTask.accessToken;

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        requestHeaders.set("Connection", "close");
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());

        HashMap<String, String> requestMap = new HashMap<>();
        requestMap.put("code", code);

        try {
            // 请求微信服务器获取号码信息
            String JsonRet = mapper.writeValueAsString(requestMap);
            HttpEntity<String> entity = new HttpEntity<>(JsonRet, requestHeaders);
            HashMap<?,?> res = new RestTemplate().postForObject(url, entity, HashMap.class);

            // 校验返回结果不为空
            if (res != null && (Integer) res.get("errcode") != 0) {
                return null;
            }

            // 校验号码信息结果是否为Map<String, String>
            HashMap<String, String> phoneInfo = new HashMap<>();
            Objects.requireNonNull(res).forEach((k, v)->{

                if((k instanceof String) && !(v instanceof String) && !(v instanceof Integer)) {
                        // 此处遍历得到data的HashMap对象
                        if(v instanceof HashMap) {
                            // 此处获取到携带手机号的HashMap对象
                            Objects.requireNonNull((HashMap<?,?>) v).forEach((key, value)-> {
                                if(key instanceof String && value instanceof String) {
                                    phoneInfo.put((String) key,(String) value);
                                }
                            });
                        }
                }
            });

            String countryCode = phoneInfo.get("countryCode");
            String purePhoneNumber = phoneInfo.get("purePhoneNumber");

            if(StringUtils.hasText(countryCode) || StringUtils.hasText(purePhoneNumber)) {
                return null;
            }

            String phone = "+"
                    + countryCode
                    + " "
                    + purePhoneNumber;

            if (userMapper.updateByOpenId(new User.Builder()
                                                  .openId(openId)
                                                  .phone(phone)
                                                  .build()) == 0)
            {
                return null;
            }

            return phone;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @author FAll
     * @description 根据openid获取用户基本信息
     * @param openId openid
     * @return: com.fall.robok.vo.UserBasicInfo
     * @date 2023/4/4 11:40
     */
    @Override
    public UserBasicInfo getUserBasicInfo(String openId) {
        return userMapper.getUserBasicInfoByOpenId(openId);
    }
}
