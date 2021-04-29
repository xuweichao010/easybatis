package com.xwc.open.easybatis.crud;

import com.xwc.open.easybatis.start.Application;
import com.xwc.open.easybatis.start.entity.User;
import com.xwc.open.easybatis.start.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/14
 * 描述：SelectSqlTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SelectSqlTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        System.out.println(">>>>>>>>>>>>>>    findBy      <<<<<<<<<<<<<<<<<<<<<");
        List<User> userList = userMapper.findBy("曹操", "200");
        System.out.println(">>>>>>>>>>>>>>    findByDynamic      <<<<<<<<<<<<<<<<<<<<<");
        List<User> userListDynamic = userMapper.findByDynamic(null, "200");
        System.out.println(">>>>>>>>>>>>>>    findByColumn      <<<<<<<<<<<<<<<<<<<<<");
        List<User> userListColumn = userMapper.findByColumn("曹操", "200");
    }
}