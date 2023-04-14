package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.AdminManagerMapper;
import com.fall.adminserver.model.Admin;
import com.fall.adminserver.model.vo.AdminLoginVo;
import com.fall.adminserver.model.vo.AdminRegisterVo;
import com.fall.adminserver.model.SecurityLoginUser;
import com.fall.adminserver.service.AdminManagerService;
import com.fall.adminserver.utils.JwtUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
@Service
public class AdminManagerServiceImpl implements AdminManagerService {

    private final AdminManagerMapper adminManagerMapper;

    private final RedisTemplate<Object,Object> redisTemplate;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminManagerServiceImpl(AdminManagerMapper adminManagerMapper,
                                   RedisTemplate<Object, Object> redisTemplate,
                                   AuthenticationManager authenticationManager,
                                   BCryptPasswordEncoder passwordEncoder) {
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.adminManagerMapper = adminManagerMapper;
    }

    @Override
    public Boolean register(AdminRegisterVo adminVo) {

        //TODO: 检查是否重名

        String id = String.valueOf(new Date().getTime());
        int ret = adminManagerMapper.register(new Admin(id,
                adminVo.getName(),
                passwordEncoder.encode(adminVo.getPassword()),
                adminVo.getAuthority()));

        return ret == 1;
    }

    @Override
    public String login(AdminLoginVo admin) {

        // 通过 AuthenticationManager 的 authenticate方法进行管理员认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(admin.getName(),admin.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        // 认证没通过，给出对应提示;
        if(Objects.isNull(authentication)) {
            throw new RuntimeException("登录失败");
        }

        // 认证通过了，使用管理员的id生成一个jwt
        Object principal = authentication.getPrincipal();

        if(principal instanceof SecurityLoginUser securityLoginUser) {
            String id = securityLoginUser.getAdmin().getId();
            String  jwt = JwtUtil.createJWT(id);

            // 把完整的管理员信息存入redis id作为key
            redisTemplate.opsForValue().set("login:"+id, securityLoginUser);

            return jwt;

        } else {
            throw new RuntimeException("登录异常");
        }
    }
}
