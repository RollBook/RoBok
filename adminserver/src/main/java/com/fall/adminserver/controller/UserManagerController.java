package com.fall.adminserver.controller;

import com.fall.adminserver.model.vo.ResponseRecord;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FAll
 * @date 2023/4/13 下午2:41
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserManagerController {

    @Operation(summary = "获取用户列表")
    @GetMapping("/get_user_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo> getUserList(){

        return null;
    }
}
