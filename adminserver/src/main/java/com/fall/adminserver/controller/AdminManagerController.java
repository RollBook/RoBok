package com.fall.adminserver.controller;

import com.fall.adminserver.model.vo.AdminLoginVo;
import com.fall.adminserver.model.vo.AdminRegisterVo;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.AdminManagerService;
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
 * @date 2023/4/13 下午2:40
 * 管理所有admin账户
 */
@RestController
@RequestMapping("/admin")
public class AdminManagerController {

    private final AdminManagerService adminManagerService;

    public AdminManagerController(AdminManagerService adminManagerService) {
        this.adminManagerService = adminManagerService;
    }

    /**
     * @author FAll
     * @description 新增管理员
     * @param admin 管理员注册vo
     * @return: com.fall.adminserver.model.vo.ResponseRecord<java.lang.Void>
     * @date 2023/4/13 下午5:52
     */
    @Operation(summary = "新增管理员")
    @PostMapping("/register")
    ResponseRecord<Void> adminRegister(@Valid @RequestBody AdminRegisterVo admin) {

        if(adminManagerService.register(admin)) {
            return ResponseRecord.success();
        }
        return ResponseRecord.fail(HttpServletResponse.SC_BAD_REQUEST,"注册失败");
    }

    /**
     * @author FAll
     * @description
     * @param admin 管理员登录vo
     * @return: com.fall.adminserver.model.vo.ResponseRecord<java.lang.Void>
     * @date 2023/4/14 下午4:07
     */
    @Operation(summary = "管理员登录")
    @PostMapping("/login")
    ResponseRecord<String> adminLogin(@Valid @RequestBody AdminLoginVo admin) {

        // 管理员登录
        return Optional.ofNullable(adminManagerService.login(admin))
                        .map(ResponseRecord::success)
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_UNAUTHORIZED));
    }

}
