package com.fall.adminserver.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author FAll
 * @date 2023/4/13 下午4:00
 * 管理员实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    String id;          // id

    String name;        // 昵称

    @JsonIgnore
    String password;    // 密码

    Integer authority;  // 权限
}
