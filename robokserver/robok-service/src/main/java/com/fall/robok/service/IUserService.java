package com.fall.robok.service;

import com.fall.robok.model.User;

import java.util.List;
import java.util.Map;

/**
 * @author FAll
 * @date 2022/11/21 10:27
 */
public interface IUserService {

    /**
     * @author FAll
     * @description 获取所有用户信息
     * @return: java.util.List<com.fall.robok.model.User>
     * @date 2022/11/21 10:41
     */
    List<User> getAllUser();

    /**
     * @param code
     * @param nickName
     * @author FAll
     * @description 用户登录&用户注册
     * @return: java.util.Map
     * @date 2022/11/21 10:42
     */
    Map SignInAndSignUp(String code, String nickName) throws Exception;

    /**
     * @param openId
     * @param sessionKey
     * @author FAll
     * @description 检查用户登录信息是否过期
     * @return: java.lang.Boolean
     * @date 2022/11/21 10:42
     */
    Boolean isLogin(String openId, String sessionKey);

    /**
     * @param code
     * @param openId
     * @author FAll
     * @description 获取用户手机号
     * @return: java.lang.String
     * @date 2022/11/21 10:42
     */
    String getPhoneNum(String code, String openId);


}
