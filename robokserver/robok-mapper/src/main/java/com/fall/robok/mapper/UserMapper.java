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

@Mapper
@Repository
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
     * @param openId
     * @author FAll
     * @description 查询是否存在该用户
     * @return: java.lang.Integer
     * @date 2022/9/24 10:43
     */
    Integer userIsExist(@Param("openId") String openId);

    /**
     * @author FAll
     * @description 新用户注册
     * @param openId
     * @param nickName
     * @param createdTime
     * @return: java.lang.Integer
     * @date 2022/9/24 12:29
     */
    Integer userSignUp(@Param("openId") String openId, @Param("nickName") String nickName, @Param("createdTime") String createdTime);




}
