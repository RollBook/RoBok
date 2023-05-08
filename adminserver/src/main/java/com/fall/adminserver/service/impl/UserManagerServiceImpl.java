package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.UserManagerMapper;
import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.User;
import com.fall.adminserver.service.UserManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/20 下午1:30
 * 用户管理serviceImpl
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {

    private final UserManagerMapper userManagerMapper;

    public UserManagerServiceImpl(UserManagerMapper userManagerMapper){
        this.userManagerMapper = userManagerMapper;
    }

    // TODO: 等待用户表学校信息改善
    @Override
    public PageInfo<User> getUserList(Integer pageNum, Integer pageSize, String order, String orderProp) {
        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<User> userList = userManagerMapper.getUserList(null, order, orderProp);
        return new PageInfo<>(userList,5);
    }

    @Override
    public PageInfo<User> getUserByName(String nickName, Integer pageNum,
                                        Integer pageSize, String order, String orderProp) {

        PageHelper.startPage(pageNum,pageSize);

        // orderProp转下划线命名
        if(Objects.nonNull(orderProp)) {
            orderProp = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,orderProp);
        }
        List<User> userList = userManagerMapper.getUserList('%'+nickName+'%', order, orderProp);
        return new PageInfo<>(userList,5);
    }
}
