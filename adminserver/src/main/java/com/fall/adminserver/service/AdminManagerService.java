package com.fall.adminserver.service;

import com.fall.adminserver.model.vo.AdminLoginVo;
import com.fall.adminserver.model.vo.AdminRegisterVo;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
public interface AdminManagerService {

    Boolean register(AdminRegisterVo adminRegisterVo);

    String login(AdminLoginVo adminLoginVo);
}
