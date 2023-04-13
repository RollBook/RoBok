package com.fall.adminserver.service.impl;

import com.fall.adminserver.mapper.AdminManagerMapper;
import com.fall.adminserver.model.Admin;
import com.fall.adminserver.model.vo.AdminVo;
import com.fall.adminserver.service.AdminManagerService;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author FAll
 * @date 2023/4/13 下午3:21
 * 管理所有admin账户
 */
@Service
public class AdminManagerServiceImpl implements AdminManagerService {

    private final AdminManagerMapper adminManagerMapper;

    public AdminManagerServiceImpl(AdminManagerMapper adminManagerMapper) {
        this.adminManagerMapper = adminManagerMapper;
    }

    @Override
    public Boolean register(AdminVo adminVo) {

        String id = UUID.randomUUID().toString();
        int ret = adminManagerMapper.register(new Admin(id,
                adminVo.getName(),
                adminVo.getPassword(),
                adminVo.getAuthority()));

        return ret == 1;
    }
}
