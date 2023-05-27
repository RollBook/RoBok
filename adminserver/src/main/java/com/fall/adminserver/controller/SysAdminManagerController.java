package com.fall.adminserver.controller;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.SysUser;
import com.fall.adminserver.model.User;
import com.fall.adminserver.model.vo.SysUserRegisterVo;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.SysAdminManagerService;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "获取客服列表")
    @GetMapping("/get_service_admin")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<SysUser>> getServiceAdmin(@RequestParam("pageNum")Integer pageNum,
                                                             @RequestParam("pageSize")Integer pageSize) {

        return ResponseRecord.success(adminManagerService.getServiceAdmin(pageNum,pageSize));
    }

    @Operation(summary = "查询客服")
    @GetMapping("/get_service_by_name")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<SysUser>> getServiceAdminByName(@RequestParam("name")String name,
                                                                @RequestParam("pageNum")Integer pageNum,
                                                             @RequestParam("pageSize")Integer pageSize) {
        return ResponseRecord.success(adminManagerService.getServiceAdminByName(name,pageNum,pageSize));
    }

    @Operation(summary = "修改客服信息")
    @PostMapping("/update_service_admin")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> updateUser(@RequestBody SysUser sysUser){

        return Optional.ofNullable(adminManagerService.updateServiceAdmin(sysUser))
                .map(e->(ResponseRecord.success("修改成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "删除客服")
    @PostMapping("/del_service_admin")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> delServiceAdmin(@RequestParam String id){
        return Optional.ofNullable(adminManagerService.delServiceAdmin(id))
                .map(e->(ResponseRecord.success("删除成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

}
