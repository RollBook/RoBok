package com.fall.adminserver.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author FAll
 * @date 2023/4/16 下午1:25
 * 权限枚举
 */
@Getter
@ToString
@AllArgsConstructor
public enum AuthorityEnum {

    ROOT(0),                // 最高管理员
    ADMIN(1),               // 管理员
    CUSTOMER_SERVICE(2);    // 客服

    private final int auth;

    /**
     * @author FAll
     * @description int转换为枚举
     * @param value 权限int值
     * @return: com.fall.adminserver.constant.AuthorityEnum
     * @date 2023/4/16 下午2:52
     */
    public static AuthorityEnum intToAuthority(int value) {
        try {
            return AuthorityEnum.values()[value];
        } catch (Exception e) {
            throw new RuntimeException(String.format("没有该int对应的权限 %s",e));
        }
    }

    /**
     * @author FAll
     * @description 权限名转换为枚举
     * @param authorityName 权限名
     * @return: com.fall.adminserver.constant.AuthorityEnum
     * @date 2023/4/16 下午3:20
     */
    public static AuthorityEnum nameToAuthority(String authorityName) {
        return switch (authorityName) {
            case "root","ROOT" -> ROOT;
            case "admin","ADMIN" -> ADMIN;
            case "customService","CUSTOM_SERVICE" -> CUSTOMER_SERVICE;
            default -> throw new IllegalStateException( "未知权限: " + authorityName); };
    }

}
