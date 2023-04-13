package com.fall.adminserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author FAll
 * @date 2023/4/13 下午4:00
 * 管理员实体
 */
@Data
@AllArgsConstructor
public class Admin{
    String id;          // id

    String name;        // 昵称

    @JsonIgnore
    String password;    // 密码

    int authority;      // 权限
}
