package com.fall.adminserver.filter;

import com.fall.adminserver.model.SecurityLoginUser;
import com.fall.adminserver.model.vo.ResponseRecord;
import com.fall.adminserver.utils.JwtUtil;
import com.fall.adminserver.utils.WebUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

/**
 * @author FAll
 * @date 2023/4/16 下午12:10
 */
@Component
@NonNullApi
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final ObjectMapper mapper;

    private final RedisTemplate<Object,Object> redisTemplate;

    public JwtAuthenticationTokenFilter(RedisTemplate<Object,Object> redisTemplate,
                                        JwtUtil jwtUtil, ObjectMapper mapper){
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
        this.redisTemplate = redisTemplate;
    }


    /**
     * @author FAll
     * @description 系统用户jwt校验过滤器
     * @param request http请求
     * @param response http响应
     * @param filterChain 过滤器链
     * @date 2023/4/16 下午12:12
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        if(!StringUtils.hasText(token)){
            // 没有token头，放行但不设置为校验成功
            filterChain.doFilter(request,response);
            return;
        }

        try {

            // 解析token，并获取其中的userid
            Claims claims;
            try {
                claims = jwtUtil.parseJWT(token);
            } catch (JwtException e) {
                throw new AuthenticationException("登录已过期");
            }
            String userid = claims.getSubject();

            // 从redis中获取用户信息
            Object o = redisTemplate.opsForValue().get("login:" + userid);
            if(o instanceof SecurityLoginUser securityLoginUser) {

                // 存入SecurityContextHolder (利用三个参数的UsernamePasswordAuthenticationToken构造方法，让认证状态设置为已认证)
                // SecurityLoginUser.getAuthorities()获取权限信息，并封装到Authentication中
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(securityLoginUser,
                                null,securityLoginUser.getAuthorities()));

                // 完成本过滤器校验，放行
                filterChain.doFilter(request,response);
            } else {
                throw new AuthenticationException("登录已过期");
            }

        } catch (JwtException | IOException | ServletException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String json = mapper
                    .writeValueAsString(ResponseRecord.fail(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage()) );
            WebUtil.renderString(response,json);
        }

    }

}
