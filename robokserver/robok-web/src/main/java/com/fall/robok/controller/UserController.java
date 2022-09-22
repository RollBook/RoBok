package com.fall.robok.controller;

import com.fall.robok.mapper.UserMapper;
import com.fall.robok.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author FAll
 * @date 2022/9/22 21:59
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserMapper mapper;

    @GetMapping("/get")
    public User getUser(){
        return mapper.getAllUser();
    }


}
