package com.fall.adminserver;

import com.fall.adminserver.mapper.SysAdminManagerMapper;
import com.fall.adminserver.model.vo.MenuItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class SysUserServerApplicationTests {

    @Autowired
    SysAdminManagerMapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void contextLoads() {
        System.out.println(mapper.getSysUserByName("FAll"));
    }

    @Test
    void testObject() throws JsonProcessingException {
        MenuItem menuItem = new MenuItem(1,"1","1",new ArrayList<>());

        MenuItem menuItem2 = new MenuItem(2,"2","2",new ArrayList<>());
        menuItem.setChildren(List.of(menuItem2));

        menuItem2.setId(3);
        System.out.println(objectMapper.writeValueAsString(menuItem));
    }

}
