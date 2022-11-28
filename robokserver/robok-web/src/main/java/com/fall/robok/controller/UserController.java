package com.fall.robok.controller;

import com.fall.robok.config.WxConfig;
import com.fall.robok.service.impl.UserServiceImpl;
import com.fall.robok.util.bean.ResBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author FAll
 * @date 2022/9/22 21:59
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    WxConfig config;

    @GetMapping("/get")
    public ResBean getUser() {
        return ResBean.ok("ok", userService.getAllUser());
    }

    /**
     * @author FAll
     * @description 用户登录&用户注册
     * @param code
     * @param nickName
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/23 14:50
     */
    @PostMapping("/login")
    public ResBean userLogin(String code, String nickName) throws Exception {
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
     * @author FAll
     * @description 检查用户登录信息是否过期
     * @param openId
     * @param sessionKey
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/24 15:21
     */
    @GetMapping("/check_login")
    public ResBean checkLogin(@RequestParam("openid") String openId, @RequestParam("session_key") String sessionKey) {
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
     * @author FAll
     * @description 获取用户手机号
     * @param code
     * @param openId
     * @return: com.fall.robok.util.bean.ResBean
     * @date 2022/9/25 18:12
     */
    @PostMapping("/code2phone_num")
    public ResBean getPhoneNum(@RequestParam("code") String code ,@RequestParam("openid")String openId){
        Object phoneNum = userService.getPhoneNum(code,openId);
        if (phoneNum == null) {
            return ResBean.badRequest("badRequest");
        }

        return ResBean.ok("200",(String)phoneNum);
    }

}
