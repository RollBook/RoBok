package com.fall.adminserver.controller;

import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.model.vo.SysUserLoginVo;
import com.fall.adminserver.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author FAll
 * @date 2023/4/16 下午12:00
 * 系统用户使用接口
 */
@RestController
@RequestMapping("/sys")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    /**
     * @author FAll
     * @description
     * @param admin 系统用户登录vo
     * @return: com.fall.adminserver.model.vo.ResponseRecord<java.lang.Void>
     * @date 2023/4/14 下午4:07
     */
    @Operation(summary = "系统用户登录")
    @PostMapping("/login")
    ResponseRecord<String> adminLogin(@Valid @RequestBody SysUserLoginVo admin) {
        // 管理员登录
        return Optional.ofNullable(sysUserService.login(admin))
                .map(ResponseRecord::success)
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_UNAUTHORIZED));
    }
}
