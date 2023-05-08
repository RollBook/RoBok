package com.fall.adminserver.service;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.User;
import com.github.pagehelper.PageInfo;

/**
 * @author FAll
 * @date 2023/4/20 下午1:27
 * 用户管理service
 */
public interface UserManagerService {

    PageInfo<User> getUserList(Integer pageNum, Integer pageSize, String order, String orderProp);

    PageInfo<User> getUserByName(String nickName, Integer pageNum, Integer pageSize, String order, String orderProp);
}
