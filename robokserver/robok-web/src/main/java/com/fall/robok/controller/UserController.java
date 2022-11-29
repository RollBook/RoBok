package com.fall.robok.controller;

import com.fall.robok.config.WxConfig;
import com.fall.robok.service.impl.UserServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.Map;


/**
 * @author FAll
 * @date 2022/9/22 21:59
 */

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    WxConfig config;

    @ApiOperation("获取所有用户")
    @GetMapping("/get")
    public ResBean getUser() {
        return ResBean.ok("ok", userService.getAllUser());
    }

    /**
     * @param code
     * @param nickName
     * @author FAll
     * @description 用户登录&用户注册
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/23 14:50
     */
    @ApiOperation("用户登录&用户注册")
    @PostMapping("/login")
    public ResBean userLogin(@NotEmpty String code,
                             @NotEmpty String nickName) throws Exception {
        Map ret = userService.SignInAndSignUp(code, nickName);
        ResBean res = null;
        if (ret == null) {
            res = ResBean.badRequest("badRequest");
        } else {
            res = ResBean.ok("ok", ret);
        }
        return res;
    }

    /**
     * @param openId
     * @param sessionKey
     * @author FAll
     * @description 检查用户登录信息是否过期
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/24 15:21
     */
    @ApiOperation("检查用户登录信息是否过期")
    @GetMapping("/check_login")
    public ResBean checkLogin(@NotEmpty @RequestParam("openid") String openId,
                              @NotEmpty @RequestParam("session_key") String sessionKey) {
        Object ret = userService.isLogin(openId, sessionKey);
        if (ret == null) {
            return ResBean.badRequest("badRequest");
        }
        boolean isLogin = (boolean) ret;
        if (isLogin) {
            return ResBean.ok("ok");
        } else {
            return ResBean.badRequest(401, "Unauthorized");
        }

    }

    /**
     * @param code
     * @param openId
     * @author FAll
     * @description 获取用户手机号
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/25 18:12
     */
    @ApiOperation("获取用户手机号")
    @PostMapping("/code2phone_num")
    public ResBean getPhoneNum(@NotEmpty @RequestParam("code") String code,
                               @NotEmpty @RequestParam("openid") String openId) {
        Object phoneNum = userService.getPhoneNum(code, openId);
        if (phoneNum == null) {
            return ResBean.badRequest("badRequest");
        }

        return ResBean.ok("200", (String) phoneNum);
    }

}
