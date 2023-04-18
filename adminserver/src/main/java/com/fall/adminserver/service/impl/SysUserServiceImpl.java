package com.fall.adminserver.service.impl;

import com.fall.adminserver.constant.MenuIDEnum;
import com.fall.adminserver.model.SecurityLoginUser;
import com.fall.adminserver.model.vo.MenuItem;
import com.fall.adminserver.model.vo.SysUserLoginVo;
import com.fall.adminserver.service.SysUserService;
import com.fall.adminserver.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    @PostConstruct
    public void initMenuLists() {

        // 初始化下属子项为空的各菜单子项
        MenuItem userItem = new MenuItem(MenuIDEnum.USER_MANAGE.getId(),
                null,null,new ArrayList<>());
        MenuItem bookItem = new MenuItem(MenuIDEnum.BOOK_MANAGE.getId(),
                null,null,new ArrayList<>());
        MenuItem orderItem = new MenuItem(MenuIDEnum.ORDER_MANAGE.getId(),
                null,null,new ArrayList<>());
        MenuItem announcementItem = new MenuItem(MenuIDEnum.ANNOUNCEMENT.getId(),
                null,null,new ArrayList<>());

        // 将菜单子项添加进菜单
        ArrayList<MenuItem> menuList =
                new ArrayList<>(Arrays.asList(userItem, bookItem, orderItem, announcementItem));

        // 初始化客服菜单
        // 用户 (客服)
        userItem.setChildren(Arrays.asList(
                // 用户管理
                new MenuItem(MenuIDEnum.USER_LIST.getId(), "/user_list","用户管理",null),
                // 用户反馈
                new MenuItem(MenuIDEnum.USER_FEEDBACK.getId(), "/user_feedback","用户反馈",null)));
        // 书本 (客服)
        bookItem.setChildren(Arrays.asList(
                // 书本管理
                new MenuItem(MenuIDEnum.BOOK_LIST.getId(), "/book_list","书本管理",null),
                // 书本审核
                new MenuItem(MenuIDEnum.BOOK_CHECK.getId(), "/book_check","书本审核",null),
                // 书本垃圾站
                new MenuItem(MenuIDEnum.BOOK_RECYCLE_BIN.getId(), "/book_recycle_bin","书本回收站",null)));
        // 订单(客服)
        orderItem.setChildren(Arrays.asList(
                // 订单管理
                new MenuItem(MenuIDEnum.ORDER_LIST.getId(), "/order_list","订单管理",null),
                // 订单垃圾站
                new MenuItem(MenuIDEnum.ORDER_RECYCLE_BIN.getId(), "/order_recycle_bin","订单回收站",null)));

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

    @Override
    public List<MenuItem> getMenuList() {
        // 获取当前用户权限等级
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof SecurityLoginUser loginUser) {
            int auth = loginUser.getAuthority().getAuth();



            // 3:root 2:管理 1：客服
            switch (auth) {
                case 1: {

                }
            }
        }
        return null;
    }
}
