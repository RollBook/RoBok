package com.fall.adminserver.model.vo;

import com.fall.adminserver.constant.AuthorityEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author FAll
 * @date 2023/4/13 下午4:26
 * 注册系统用户vo
 */
@Data
public class SysUserRegisterVo {
    @NotEmpty(message = "姓名不能为空")
    String name;

    @NotEmpty(message = "密码不能为空")
    String password;

    @NotNull(message = "权限不能为空")
    AuthorityEnum authority;
}
