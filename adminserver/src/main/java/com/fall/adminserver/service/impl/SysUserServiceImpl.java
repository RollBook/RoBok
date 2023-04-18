package com.fall.adminserver.service.impl;

import com.fall.adminserver.constant.AuthorityEnum;
import com.fall.adminserver.constant.MenuIDEnum;
import com.fall.adminserver.mapper.SysUserMapper;
import com.fall.adminserver.model.MenuSubItem;
import com.fall.adminserver.model.SecurityLoginUser;
import com.fall.adminserver.model.vo.MenuItem;
import com.fall.adminserver.model.vo.SysUserLoginVo;
import com.fall.adminserver.service.SysUserService;
import com.fall.adminserver.utils.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author FAll
 * @date 2023/4/16 下午12:06
 * 系统用户serviceImpl
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private final RedisTemplate<Object,Object> redisTemplate;

    private final AuthenticationManager authenticationManager;

    private final SysUserMapper userMapper;

    private final JwtUtil jwtUtil;

    public SysUserServiceImpl(RedisTemplate<Object, Object> redisTemplate,
                              AuthenticationManager authenticationManager,
                              SysUserMapper userMapper, JwtUtil jwtUtil) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostConstruct
    public void initMenuLists() {
        // 获取所有菜单项
        List<MenuSubItem> subItems = userMapper.getSubItems();

        // 将菜单项根据权限分组
        Map<Integer, List<MenuSubItem>> collect = subItems.stream()
                .collect(Collectors.groupingBy(MenuSubItem::getAuthority));

        MenuItem userItem = new MenuItem(MenuIDEnum.USER_MANAGE.getId(), null, "用户", null);
        MenuItem bookItem = new MenuItem(MenuIDEnum.BOOK_MANAGE.getId(), null, "书本", null);
        MenuItem orderItem = new MenuItem(MenuIDEnum.ORDER_MANAGE.getId(), null, "订单", null);
        MenuItem machineItem = new MenuItem(MenuIDEnum.MACHINE_MANAGE.getId(), null, "机器", null);
        MenuItem eventItem = new MenuItem(MenuIDEnum.ANNOUNCEMENT.getId(), null, "活动", null);

        // 初始化总菜单
        ArrayList<MenuItem> menuList = new ArrayList<>(Arrays.asList(userItem, bookItem, orderItem, machineItem, eventItem));

        // 初始化redis操作
        ValueOperations<Object, Object> redisValueOperations = redisTemplate.opsForValue();

        // 将菜单数据根据权限，添加到总菜单，并在redis中缓存不同权限的菜单

        // 客服
        // 将客服相关页面根据功能排序
        Map<Integer, List<MenuSubItem>> customerServiceCollectByModule = collect.get(AuthorityEnum.CUSTOMER_SERVICE.getAuth()).stream()
                .collect(Collectors.groupingBy((item -> item.getId() / 10)));

        customerServiceCollectByModule.forEach((entry,list)->
                menuList.forEach(menuItem -> { if(menuItem.getId() == entry) { menuItem.setChildren(list); } })
        );
        redisValueOperations.set("menu:customerService",menuList);

        // 管理员
        // 将管理员相关页面根据功能排序
        Map<Integer, List<MenuSubItem>> adminCollectByModule = collect.get(AuthorityEnum.ADMIN.getAuth()).stream()
                .collect(Collectors.groupingBy((item -> item.getId() / 10)));
        // 管理员权限页面数组和客服页面数组相加
        adminCollectByModule.forEach((entry,list)-> menuList.forEach(menuItem -> {
            if(menuItem.getId() == entry) { menuItem.appendChildren(list); } })
        );
        redisValueOperations.set("menu:admin",menuList);

        // ROOT
        // 将ROOT相关页面根据功能排序
        Map<Integer, List<MenuSubItem>> rootCollectByModule = collect.get(AuthorityEnum.ROOT.getAuth()).stream()
                .collect(Collectors.groupingBy((item -> item.getId() / 10)));
        // ROOT权限页面数组和客服页面数组相加
        rootCollectByModule.forEach((entry,list)-> menuList.forEach(menuItem -> {
                if(menuItem.getId() == entry) { menuItem.appendChildren(list); } })
        );
        redisValueOperations.set("menu:root",menuList);
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
    public List<?> getMenuList() {
        // 获取当前用户权限等级
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof SecurityLoginUser loginUser) {
            int auth = loginUser.getAuthority().getAuth();

            ValueOperations<Object, Object> redisValueOperations = redisTemplate.opsForValue();
            // 根据用户权限查询对应菜单
            // 3:root 2:管理 1：客服
            return switch (auth) {
                case 0 -> (List<?>)redisValueOperations.get("menu:root");
                case 1 -> (List<?>)redisValueOperations.get("menu:customerService");
                case 2 -> (List<?>)redisValueOperations.get("menu:admin");
                default -> throw new IllegalStateException("Unexpected value: " + auth);
            };
        }
        return null;
    }
}
