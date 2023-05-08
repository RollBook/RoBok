package com.fall.adminserver.controller;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.User;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.service.UserManagerService;
import com.fall.adminserver.service.impl.UserManagerServiceImpl;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(order);
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
}
