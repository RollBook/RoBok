package com.fall.robok.mapper;

import com.fall.robok.po.User;
import com.fall.robok.vo.UserBasicInfo;
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
     * @author FAll
     * @description 获取所有用户信息
     * @return: java.util.List<com.fall.robok.po.User>
     * @date 2022/9/23 12:59
     */
    @Select("select * from `t_user`")
    List<User> getAllUser();

    /**
     * @param openId openid
     * @author FAll
     * @description 查询是否存在该用户
     * @return: java.lang.Integer
     * @date 2022/9/24 10:43
     */
    Integer userIsExist(@Param("openId") String openId);

    /**
     * @param openId      openid
     * @param nickName    用户昵称
     * @param createdTime 注册时间
     * @author FAll
     * @description 新用户注册
     * @date 2022/9/24 12:29
     */
    void userSignUp(@Param("openId") String openId, @Param("nickName") String nickName, @Param("createdTime") String createdTime);

    /**
     * @author FAll
     * @description 根据openid更新用户信息
     * @param user 用户
     * @return: java.lang.Integer
     * @date 2022/9/25 18:22
     */
    Integer updateByOpenId(User user);

    /**
     * @author FAll
     * @description 根据openid查询基本用户信息
     * @param openid openid
     * @return: com.fall.robok.vo.SellerInfo
     * @date 2023/3/18 16:47
     */
    UserBasicInfo getUserBasicInfoByOpenId(String openid);

}
