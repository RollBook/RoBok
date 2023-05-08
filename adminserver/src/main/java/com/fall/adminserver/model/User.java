package com.fall.adminserver.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author FAll
 * @date 2022/9/22 13:59
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 用户openid
    private String openId;
    // 用户昵称
    private String nickName;
    // 用户学校
    private String school;
    // 用户注册时间
    private String createdTime;
    // 用户手机号码
    private String phone;
    // 用户邮箱
    private String email;
    // 用户地址
    private String address;
    // 用户地址纬度
    private Double latitude;
    // 用户地址经度
    private Double longitude;

}
