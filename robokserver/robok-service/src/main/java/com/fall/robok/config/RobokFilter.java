package com.fall.robok.config;

import com.fall.robok.service.impl.UserServiceImpl;
import com.fall.robok.util.bean.ResBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

// urlPatterns列出需要被过滤的Controller
@WebFilter(urlPatterns = {"/user/*","/seller/*","/order/*"}, filterName = "RobokFilter")
@Slf4j
public class RobokFilter implements Filter {
    private final UserServiceImpl userService;

    private final ServerConfig serverConfig;

    private final ObjectMapper mapper;

    @Autowired
    public RobokFilter(UserServiceImpl userService,ServerConfig serverConfig,ObjectMapper mapper){
        this.userService=userService;
        this.serverConfig=serverConfig;
        this.mapper=mapper;
    }

    private Boolean isDev = false;

    // 此处列出不用拦截的接口
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "/user/login",
            "/user/check_login"
    )));

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (serverConfig.getEnvironment().equals("dev")) {
            isDev = true;
        }
        log.info("Filter created");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length()).replaceAll("[/]+$", "");

        boolean allowedPath = ALLOWED_PATHS.contains(path);
        if (isDev) {
            allowedPath = allowedPath || path.contains(".jpg");
        }

        if (allowedPath) {
            chain.doFilter(request, response);
        } else {
            // redis校验session
            Object ret = userService.isLogin(req.getHeader("openid"), req.getHeader("session_key"));
            if (ret == null) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                returnJson(response, mapper.writeValueAsString(ResBean.badRequest(401, "登录信息已失效,请重新登录")));
                return;
            }
            boolean isLogin = (boolean) ret;
            if (isLogin) {
                chain.doFilter(request, response);
            } else {
                returnJson(response, mapper.writeValueAsString(ResBean.badRequest(401, "登录信息已失效,请重新登录")));
            }

        }

    }

    @Override
    public void destroy() {
        log.info("Filter destroyed");
    }

    /**
     * @param response http响应
     * @param json Json数据
     * @author FAll
     * @description 拦截器内响应
     * @date 2022/9/24 14:15
     */
    private void returnJson(ServletResponse response, String json) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()) {
            writer.print(json);

        } catch (IOException e) {
            log.error("response error", e);
        }
    }

}




