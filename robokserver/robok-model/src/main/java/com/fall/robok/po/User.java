package com.fall.robok.po;

import com.fall.robok.vo.UserBasicInfo;
import lombok.*;

/**
 * @author FAll
 * @date 2022/9/22 13:59
 */

@Data
@ToString
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

    private User(Builder builder) {
        this.openId = builder.openId;
        this.nickName = builder.nickName;
        this.school = builder.school;
        this.createdTime = builder.createdTime;
        this.phone = builder.phone;
        this.email = builder.email;
        this.address = builder.address;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
    }

    /**
     * @author FAll
     * @date 2023/3/6 18:01
     * @description 用户建造者
     */
    public static final class Builder {
        private String openId;
        private String nickName;
        private String school;
        private String createdTime;
        private String phone;
        private String email;
        private String address;
        private Double latitude;
        private Double longitude;

        /**
         * @author FAll
         * @description 使用建造者创建用户对象
         * @return: com.fall.robok.po.User
         * @date 2023/3/6 18:10
         */
        public User build(){
            return new User(this);
        }

        public Builder openId(String openId) {
            this.openId = openId;
            return this;
        }

        public Builder nickName(String nickName) {
            this.nickName = nickName;
            return this;
        }
        public Builder school(String school) {
            this.school = school;
            return this;
        }

        public Builder createdTime(String createdTime) {
            this.createdTime = createdTime;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder userBasicInfo(UserBasicInfo userBasicInfo) {
            this.nickName = userBasicInfo.getNickName();
            this.school = userBasicInfo.getSchool();
            this.phone = userBasicInfo.getPhone();
            this.address = userBasicInfo.getAddress();
            this.latitude = userBasicInfo.getLatitude();
            this.longitude = userBasicInfo.getLongitude();
            return this;
        }
    }

}
