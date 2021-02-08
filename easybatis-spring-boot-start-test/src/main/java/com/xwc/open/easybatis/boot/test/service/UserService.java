package com.xwc.open.easybatis.boot.test.service;

import com.xwc.open.easybatis.boot.test.entity.User;
import com.xwc.open.easybatis.boot.test.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void init() {
        System.out.println(userMapper);
        User user = userMapper.findByName("曹操");
        System.out.println(user);
    }
}
