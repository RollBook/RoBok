package com.fall.robok.config;

import com.alibaba.fastjson.JSON;
import com.fall.robok.service.UserService;
import com.fall.robok.model.ResBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author FAll
 * @date 2022/9/23 19:22
 */

@Component
@WebFilter(urlPatterns = "/admin/*", filterName = "RobokFilter")
@Slf4j
public class RobokFilter implements Filter {

    @Autowired
    UserService userService;
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "/user/login",
            "/user/check_login",
            "/rollbook/get_index_swiper"
            )));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[ {} ] Created......", this.getClass().getSimpleName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if (allowedPath) {
            chain.doFilter(request, response);
        } else {
            // TODO: redis校验session
            Object ret = userService.isLogin(req.getHeader("openid"), req.getHeader("session_key"));
            if (ret == null) {
                returnJson(response, JSON.toJSONString(ResBean.badRequest(401, "登录信息已失效,请重新登录")));
                return;
            }
            boolean isLogin = (boolean) ret;
            if (isLogin) {
                chain.doFilter(request, response);
            } else {
                returnJson(response, JSON.toJSONString(ResBean.badRequest(401, "登录信息已失效,请重新登录")));
            }

        }

    }

    @Override
    public void destroy() {
        log.info("[ {} ] Destroyed......", this.getClass().getSimpleName());
    }

    /**
     * @param response
     * @param json
     * @author FAll
     * @description 拦截器内响应
     * @date 2022/9/24 14:15
     */
    private void returnJson(ServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            log.error("response error", e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }

}



