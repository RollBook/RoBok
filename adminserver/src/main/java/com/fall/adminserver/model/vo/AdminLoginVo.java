package com.fall.adminserver.model.vo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * @author FAll
 * @date 2023/4/14 下午4:03
 * 管理员登录vo
 */
@Data
public class AdminLoginVo {

    @NotEmpty(message = "名称不可以为空")
    String name;        // 昵称

    @NotEmpty(message = "密码不可以为空")
    String password;    // 密码

}
