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

    /**
     * @author FAll
     * @description 程序启动时初始化不同权限用户的菜单
     * @date 2023/4/19 上午12:31
     */
    @PostConstruct
    public void initMenuLists() {
        // 初始化redis操作
        ValueOperations<Object, Object> redisValueOperations = redisTemplate.opsForValue();

        // 获取所有菜单项
        List<MenuSubItem> subItems = userMapper.getSubItems();

        // 将菜单页面根据权限分组
        Map<Integer, List<MenuSubItem>> collect = subItems.stream()
                .collect(Collectors.groupingBy(MenuSubItem::getAuthority));

        // 将不同页面存入redis
        // 客服
        ArrayList<MenuSubItem> menuSubItemsToCache = new ArrayList<>(collect.get(2));
        redisValueOperations.set("page:customerService",menuSubItemsToCache);
        // 管理员
        menuSubItemsToCache.addAll(collect.get(1));
        redisValueOperations.set("page:admin",menuSubItemsToCache);
        // root
        menuSubItemsToCache.addAll(collect.get(0));
        redisValueOperations.set("page:root",menuSubItemsToCache);

        // 初始化子菜单
        MenuItem userItem = new MenuItem(MenuIDEnum.USER_MANAGE.getId(), null, "用户管理", null);
        MenuItem bookItem = new MenuItem(MenuIDEnum.BOOK_MANAGE.getId(), null, "书本管理", null);
        MenuItem orderItem = new MenuItem(MenuIDEnum.ORDER_MANAGE.getId(), null, "订单管理", null);
        MenuItem machineItem = new MenuItem(MenuIDEnum.MACHINE_MANAGE.getId(), null, "机器管理", null);
        MenuItem eventItem = new MenuItem(MenuIDEnum.ANNOUNCEMENT.getId(), null, "活动管理", null);

        // 初始化总菜单
        ArrayList<MenuItem> menuList = new ArrayList<>(Arrays.asList(userItem, bookItem,
                orderItem, machineItem, eventItem));

        /* 将菜单数据根据权限，添加到总菜单，并在redis中缓存不同权限的菜单 */
        // 客服
        // 将客服相关页面根据功能排序
        Map<Integer, List<MenuSubItem>> customerServiceCollectByModule = collect.get(AuthorityEnum.CUSTOMER_SERVICE.getAuth()).stream()
                .collect(Collectors.groupingBy((item -> item.getId() / 10)));

        customerServiceCollectByModule.forEach((entry,list)->
                menuList.forEach(menuItem -> { if (menuItem.getId() == entry) { menuItem.setChildren(list); } })
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
            // 获取系统用户id
            String id = securityLoginUser.getSysUser().getId();
            // 把完整的系统用户信息存入redis id作为key
            redisTemplate.opsForValue().set("login:"+id, securityLoginUser);

            // 签发并返回token
            return jwtUtil.createJWT(id);

        } else {
            throw new RuntimeException("登录异常");
        }
    }

    @Override
    public Object getMenuList() {
        // 获取当前用户权限等级
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if(authentication.getPrincipal() instanceof SecurityLoginUser loginUser) {
            int auth = loginUser.getAuthority().getAuth();

            ValueOperations<Object, Object> redisValueOperations = redisTemplate.opsForValue();
            // 根据用户权限查询对应菜单
            // 0:root 1:管理 2：客服
            return switch (auth) {
                case 0 -> (List<?>)redisValueOperations.get("menu:root");
                case 1 -> (List<?>)redisValueOperations.get("menu:customerService");
                case 2 -> (List<?>)redisValueOperations.get("menu:admin");
                default -> throw new IllegalStateException("Unexpected value: " + auth);
            };
        }
        return null;
    }

    @Override
    public Boolean checkPageAuth(String path) {
        // 获取当前用户权限等级
        Authentication authentication = SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof SecurityLoginUser loginUser) {
            int auth = loginUser.getAuthority().getAuth();

            // 获取当前权限可以访问的的所有页面
            ValueOperations<Object, Object> redisValueOperations = redisTemplate.opsForValue();
            Object o = switch (auth) {
                case 0 -> redisValueOperations.get("page:root");
                case 1 -> redisValueOperations.get("page:admin");
                case 2 -> redisValueOperations.get("page:customerService");
                default -> throw new IllegalStateException("Unexpected value: " + auth);
            };

            // 检查访问的页面是否在可访问范围内
            if(o instanceof List<?> list) {
                return list.stream()
                        // 对list中所有对象进行类型检查，并转化为MenuSubItem
                        .map(listItem->{
                            if(listItem instanceof MenuSubItem subItem) { return subItem; }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        // 判断获得的可访问页面list中是否存在path对应页面
                        .anyMatch(matchItem -> matchItem.getPath().equals(path));
            }
            return false;
        }
        return false;
    }
}
