package com.fall.adminserver.service;

import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.SysUser;
import com.fall.adminserver.model.vo.SysUserLoginVo;
import com.fall.adminserver.model.vo.SysUserRegisterVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
public interface SysAdminManagerService {

    Boolean register(SysUserRegisterVo sysUserRegisterVo);

    PageInfo<SysUser> getServiceAdmin(Integer pageNum, Integer pageSize);

    PageInfo<SysUser> getServiceAdminByName(String name,Integer pageNum, Integer pageSize);

    Integer updateServiceAdmin(SysUser sysUser);

    Integer delServiceAdmin(String id);

}
