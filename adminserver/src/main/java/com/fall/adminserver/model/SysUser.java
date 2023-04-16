package com.fall.adminserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FAll
 * @date 2023/4/13 下午4:00
 * 系统用户实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements Serializable {
    String id;          // id

    String name;        // 昵称

    @JsonIgnore
    String password;    // 密码

    int authority;  // 权限
}
