package com.fall.adminserver.service;

import com.github.pagehelper.PageInfo;

/**
 * @author FAll
 * @date 2023/4/20 下午1:27
 * 用户管理service
 */
public interface UserManagerService {

    PageInfo getUserList();
}
