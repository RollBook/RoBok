package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.AdminManagerMapper;
import com.fall.adminserver.model.Admin;
import com.fall.adminserver.model.vo.AdminRegisterVo;
import com.fall.adminserver.service.AdminManagerService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
@Service
public class AdminManagerServiceImpl implements AdminManagerService {

    private final AdminManagerMapper adminManagerMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminManagerServiceImpl(AdminManagerMapper adminManagerMapper,
                                   BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.adminManagerMapper = adminManagerMapper;
    }

    @Override
    public Boolean register(AdminRegisterVo adminVo) {

        //TODO: 检查是否重名

        String id = String.valueOf(new Date().getTime());
        int ret = adminManagerMapper.register(new Admin(id,
                adminVo.getName(),
                passwordEncoder.encode(adminVo.getPassword()),
                adminVo.getAuthority()));

        return ret == 1;
    }

}
