package com.fall.robok.mapper;

import com.fall.robok.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
    @Select("select * from `t_user`")
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
     * @param openId
     * @param nickName
     * @param createdTime
     * @author FAll
     * @description 新用户注册
     * @return: java.lang.Integer
     * @date 2022/9/24 12:29
     */
    Integer userSignUp(@Param("openId") String openId, @Param("nickName") String nickName, @Param("createdTime") String createdTime);

    /**
     * @author FAll
     * @description 根据openid更新用户信息
     * @param user
     * @return: java.lang.Integer
     * @date 2022/9/25 18:22
     */
    Integer updateByOpenId(User user);


}
