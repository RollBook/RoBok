package com.fall.adminserver.controller;

import com.fall.adminserver.model.Announcement;
import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.User;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.UserManagerService;
import com.fall.adminserver.service.impl.UserManagerServiceImpl;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author FAll
 * @date 2023/4/13 下午2:41
 * 用户管理
 */
@RestController
@RequestMapping("/user")
public class UserManagerController {

    private final UserManagerService userManagerService;

    public UserManagerController(UserManagerService userManagerService){
        this.userManagerService = userManagerService;
    }

    @Operation(summary = "获取用户列表")
    @GetMapping("/get_user_list")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<User>> getUserList(@RequestParam("pageNum")Integer pageNum,
                                                      @RequestParam("pageSize")Integer pageSize,
                                                      @Nullable @RequestParam("order")String order,
                                                      @Nullable @RequestParam("order_prop")String orderProp){
        return ResponseRecord.success(userManagerService.getUserList(pageNum,pageSize,order,orderProp));
    }

    @Operation(summary = "根据名称获取用户")
    @GetMapping("/get_user_by_name")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<PageInfo<User>> getBookByName(@RequestParam("nickName")String nickName,
                                                        @RequestParam("pageNum")Integer pageNum,
                                                        @RequestParam("pageSize")Integer pageSize,
                                                        @Nullable @RequestParam("order")String order,
                                                        @Nullable @RequestParam("order_prop")String orderProp) {

        return ResponseRecord
                .success(userManagerService.getUserByName(nickName,pageNum,pageSize,order,orderProp));

    }

    @Operation(summary = "删除用户")
    @PostMapping("/del_user")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> delUser(@RequestParam String openId){
        return Optional.ofNullable(userManagerService.delUser(openId))
                .map(e->(ResponseRecord.success("删除成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "修改用户")
    @PostMapping("/update_user")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    ResponseRecord<Integer> updateUser(@RequestBody User user){
        return Optional.ofNullable(userManagerService.updateUser(user))
                .map(e->(ResponseRecord.success("修改成功",e)))
                .orElse(ResponseRecord.fail(HttpServletResponse.SC_FORBIDDEN));
    }

    @Operation(summary = "宏观获取用户列表")
    @GetMapping("/get_user_sys")
    @PreAuthorize("@ss.hasAuth('CUSTOMER_SERVICE')")
    public ResponseRecord<Map<String, List<User>>> getUserListSys(){
        return ResponseRecord.success(userManagerService.getUserListSys());
    }
}
