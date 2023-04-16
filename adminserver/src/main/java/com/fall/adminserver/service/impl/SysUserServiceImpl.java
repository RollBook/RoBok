package com.fall.adminserver.service.impl;

import com.fall.adminserver.model.SecurityLoginUser;
import com.fall.adminserver.model.vo.SysUserLoginVo;
import com.fall.adminserver.service.SysUserService;
import com.fall.adminserver.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/16 下午12:06
 * 系统用户serviceImpl
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private final RedisTemplate<Object,Object> redisTemplate;

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public SysUserServiceImpl(RedisTemplate<Object, Object> redisTemplate,
                              AuthenticationManager authenticationManager,
                              BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    @Override
    public String login(SysUserLoginVo admin) {

        // 通过 AuthenticationManager 的 authenticate方法进行管理员认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(admin.getName(),admin.getPassword());

        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 认证没通过，给出对应提示;
        if(Objects.isNull(authentication)) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        // 认证通过了，使用管理员的id生成一个jwt
        Object principal = authentication.getPrincipal();

        if(principal instanceof SecurityLoginUser securityLoginUser) {
            String id = securityLoginUser.getSysUser().getId();
            String  jwt = jwtUtil.createJWT(id);

            // 把完整的管理员信息存入redis id作为key
            redisTemplate.opsForValue().set("login:"+id, securityLoginUser);

            return jwt;

        } else {
            throw new RuntimeException("登录异常");
        }
    }
}
