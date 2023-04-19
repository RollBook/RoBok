package com.fall.adminserver.controller;

import com.fall.adminserver.model.vo.MenuItem;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.model.vo.SysUserLoginVo;
import com.fall.adminserver.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
     * @description 页面拦截检查登录
     * @return: com.fall.adminserver.model.vo.ResponseRecord<java.lang.Void>
     * @date 2023/4/18 下午1:55
     */
    @Operation(summary = "检查是否登录")
    @GetMapping("/check/page_auth")
    ResponseRecord<Void> checkLogin(@RequestParam("url") String path) {
        // 通过了JWT过滤器，说明登录未过期，直接返回成功
        if(sysUserService.checkPageAuth(path)) {
            return ResponseRecord.success();
        }
        return ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN,"权限不足");

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
    ResponseRecord<String> sysUserLogin(@Valid @RequestBody SysUserLoginVo admin) {
        // 系统用户登录
        return Optional.ofNullable(sysUserService.login(admin))
                .map(ResponseRecord::success)
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_UNAUTHORIZED));
    }

    /**
     * @author FAll
     * @description 系统用户获取菜单列表
     * @return: com.fall.adminserver.model.vo.ResponseRecord<java.util.List < com.fall.adminserver.model.vo.MenuItem>>
     * @date 2023/4/18 下午9:18
     */
    @Operation(summary = "获取菜单列表")
    @GetMapping("/get_menu_list")
    ResponseRecord<Object> getMenuList() {
        return ResponseRecord.success(sysUserService.getMenuList());
    }

}
