package com.fall.adminserver;

import com.fall.adminserver.mapper.SysAdminManagerMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SysUserServerApplicationTests {

    @Autowired
    SysAdminManagerMapper mapper;

    @Test
    void contextLoads() {
        System.out.println(mapper.getSysUserByName("FAll"));
    }

}
