package com.fall.adminserver.expression;

import com.fall.adminserver.constant.AuthorityEnum;
import com.fall.adminserver.model.SecurityLoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;



/**
 * @author FAll
 * @date 2023/4/16 下午3:05
 * springSecurity 表达式
 */
@Component("ss")
public class SecurityExpressionRoot {

    /**
     * @author FAll
     * @description 设定接口调用权限
     * @param authority 权限名称
     * @return: boolean
     * @date 2023/4/16 下午3:50
     */
    public boolean hasAuth(String authority) {
        // 将权限名转化为具体权限
        AuthorityEnum authorityEnum = AuthorityEnum.nameToAuthority(authority);

        // 获取当前用户的权限
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof SecurityLoginUser loginUser) {
            int userAuthority = loginUser.getAuthority().getAuth();
            // 判断用户权限是否足够 (值越小，权限越大)
            return userAuthority <= authorityEnum.getAuth();

        } else {
            throw new RuntimeException();
        }

    }
}
