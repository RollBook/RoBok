package com.fall.adminserver.service;

import com.fall.adminserver.model.vo.SysUserLoginVo;

/**
 * @author FAll
 * @date 2023/4/16 下午12:05
 * 系统用户service
 */
public interface SysUserService {

    String login(SysUserLoginVo sysUserLoginVo);

}
