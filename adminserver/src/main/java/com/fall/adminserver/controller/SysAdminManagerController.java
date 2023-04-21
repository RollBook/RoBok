package com.fall.adminserver.controller;

import com.fall.adminserver.model.vo.SysUserRegisterVo;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.SysAdminManagerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author FAll
 * @date 2023/4/13 下午2:40
 * 管理所有admin账户
 */
@RestController
@RequestMapping("/admin")
public class SysAdminManagerController {

    private final SysAdminManagerService adminManagerService;

    public SysAdminManagerController(SysAdminManagerService sysAdminManagerService) {
        this.adminManagerService = sysAdminManagerService;
    }

    /**
     * @author FAll
     * @description 新增新增系统用户
     * @param admin 系统用户注册vo
     * @return: com.fall.adminserver.model.vo.ResponseRecord<java.lang.Void>
     * @date 2023/4/13 下午5:52
     */
    @Operation(summary = "新增系统用户")
    @PreAuthorize("@ss.hasAuth('ADMIN')")
    @PostMapping("/register")
    ResponseRecord<Void> adminRegister(@Valid @RequestBody SysUserRegisterVo admin) {

        if(adminManagerService.register(admin)) {
            return ResponseRecord.success();
        }
        return ResponseRecord.fail(HttpServletResponse.SC_BAD_REQUEST,"该名称已被占用");
    }

}
