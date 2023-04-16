package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.SysAdminManagerMapper;
import com.fall.adminserver.model.SysUser;
import com.fall.adminserver.model.vo.SysUserRegisterVo;
import com.fall.adminserver.service.SysAdminManagerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
@Service
public class SysSysAdminManagerServiceImpl implements SysAdminManagerService {

    private final SysAdminManagerMapper adminManagerMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public SysSysAdminManagerServiceImpl(SysAdminManagerMapper sysAdminManagerMapper,
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
                adminVo.getAuthority()));

        return ret == 1;
    }

}
