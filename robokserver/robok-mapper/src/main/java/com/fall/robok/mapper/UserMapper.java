package com.fall.robok.mapper;

import com.fall.robok.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author FAll
 * @date 2022/9/22 14:05
 */
@Repository
@Mapper
public interface UserMapper {

    /**
     * @param
     * @author FAll
     * @description 获取所有用户信息
     * @return: java.util.List<com.fall.robok.model.User>
     * @date 2022/9/23 12:59
     */
    List<User> getAllUser();

    /**
     * @param code
     * @param nickName
     * @author FAll
     * @description 新用户注册
     * @return: java.lang.Integer
     * @date 2022/9/23 14:29
     */
    Integer userSignUp(@Param("code") String code, @Param("nickName") String nickName);

    /**
     * @param code
     * @param nickName
     * @author FAll
     * @description 用户登录
     * @return: java.lang.Integer
     * @date 2022/9/23 14:29
     */
    Integer userSignIn(@Param("code") String code, @Param("nickName") String nickName);

}
