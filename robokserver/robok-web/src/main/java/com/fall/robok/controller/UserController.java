package com.fall.robok.controller;

import com.fall.robok.service.impl.UserServiceImpl;
import com.fall.robok.util.bean.ResBean;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.util.HashMap;


/**
 * @author FAll
 * @date 2022/9/22 21:59
 */
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @ApiOperation("获取所有用户")
    @GetMapping("/get")
    public ResBean getUser() {
        return ResBean.ok("ok", userService.getAllUser());
    }

    /**
     * @param code     微信code
     * @param nickName 用户昵称
     * @author FAll
     * @description 用户登录&用户注册
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/23 14:50
     */
    @ApiOperation("用户登录&用户注册")
    @PostMapping("/login")
    public ResBean userLogin(@NotEmpty String code, @Nullable String nickName,
                             HttpServletResponse response) throws Exception {
        HashMap<String, String> ret = userService.SignInAndSignUp(code, nickName);
        ResBean res;
        if (ret == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res = ResBean.badRequest("badRequest");
        } else {
            res = ResBean.ok("ok", ret);
        }
        return res;
    }

    /**
     * @param openId     openid
     * @param sessionKey 会话密钥
     * @author FAll
     * @description 检查用户登录信息是否过期
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/24 15:21
     */
    @ApiOperation("检查用户登录信息是否过期")
    @GetMapping("/check_login")
    public ResBean checkLogin(@NotEmpty @RequestParam("openid") String openId,
                              @NotEmpty @RequestParam("session_key") String sessionKey,
                              HttpServletResponse response) {
        Object ret = userService.isLogin(openId, sessionKey);
        if (ret == null) {
            return ResBean.badRequest("badRequest");
        }
        boolean isLogin = (boolean) ret;
        if (isLogin) {
            return ResBean.ok("ok");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return ResBean.unauthorized("Unauthorized");
        }

    }

    /**
     * @param code   微信code
     * @param openId openid
     * @author FAll
     * @description 获取用户手机号
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/25 18:12
     */
    @ApiOperation("获取用户手机号")
    @GetMapping("/code2phone_num")
    public ResBean getPhoneNum(@NotEmpty @RequestParam("code") String code,
                               @NotEmpty @RequestParam("openid") String openId) {

        Object phoneNum = userService.getPhoneNum(code, openId);
        if (phoneNum == null) {
            return ResBean.badRequest("badRequest");
        }

        return ResBean.ok("ok", phoneNum);
    }

}
