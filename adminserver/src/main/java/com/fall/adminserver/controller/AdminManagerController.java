package com.fall.adminserver.controller;

import com.fall.adminserver.model.vo.AdminRegisterVo;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.AdminManagerService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
     * @param admin 管理员
     * @param response http响应
     * @return: com.fall.adminserver.model.vo.ResponseRecord<java.lang.Void>
     * @date 2023/4/13 下午5:52
     */
    @PostMapping("/register")
    ResponseRecord<Void> adminRegister(@Valid @RequestBody AdminRegisterVo admin, HttpServletResponse response) {

        boolean ret = adminManagerService.register(admin);
        if(ret) {
            return ResponseRecord.success();
        }
        return ResponseRecord.fail(response.SC_BAD_REQUEST,"注册失败");
    }

}
