package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.SysAdminManagerMapper;
import com.fall.adminserver.model.Book;
import com.fall.adminserver.model.Order;
import com.fall.adminserver.model.SysUser;
import com.fall.adminserver.model.vo.SysUserRegisterVo;
import com.fall.adminserver.service.SysAdminManagerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.CaseFormat;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
@Service
public class SysAdminManagerServiceImpl implements SysAdminManagerService {

    private final SysAdminManagerMapper adminManagerMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public SysAdminManagerServiceImpl(SysAdminManagerMapper sysAdminManagerMapper,
                                      BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.adminManagerMapper = sysAdminManagerMapper;
    }

    @Override
    public Boolean register(SysUserRegisterVo adminVo) {
        // TODO: 检查注册目标权限是否高于自身

        // 检查是否重名
        if(Objects.nonNull(adminManagerMapper.getSysUserByName(adminVo.getName()))) {
            return false;
        }

        String id = String.valueOf(new Date().getTime());
        int ret = adminManagerMapper.register(new SysUser(id,
                adminVo.getName(),
                passwordEncoder.encode(adminVo.getPassword()),
                adminVo.getAuthority().getAuth()));

        return ret == 1;
    }

    @Override
    public PageInfo<SysUser> getServiceAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<SysUser> adminList = adminManagerMapper.getAdmin(null);

        List<SysUser> toRemove = new ArrayList<>();
        for(SysUser s : adminList) {
            if(s.getAuthority() != 2) {
                toRemove.add(s);
            }
        }
        adminList.removeAll(toRemove);
        return new PageInfo<>(adminList,5);
    }

    @Override
    public PageInfo<SysUser> getServiceAdminByName(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);

        List<SysUser> adminList = adminManagerMapper.getAdmin(name);

        List<SysUser> toRemove = new ArrayList<>();
        for(SysUser s : adminList) {
            if(s.getAuthority() != 2) {
                toRemove.add(s);
            }
        }
        adminList.removeAll(toRemove);
        return new PageInfo<>(adminList,5);
    }

    @Override
    public Integer updateServiceAdmin(SysUser sysUser) {
        return adminManagerMapper.updateServiceAdmin(new SysUser(sysUser.getId(),
                sysUser.getName(),
                passwordEncoder.encode(sysUser.getPassword()),
                sysUser.getAuthority()));
    }

    @Override
    public Integer delServiceAdmin(String id) {
        return adminManagerMapper.delServiceAdmin(id);
    }

}
