package com.fall.adminserver.service;

import com.fall.adminserver.model.vo.SysUserLoginVo;
import com.fall.adminserver.model.vo.SysUserRegisterVo;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
public interface SysAdminManagerService {

    Boolean register(SysUserRegisterVo sysUserRegisterVo);

}
