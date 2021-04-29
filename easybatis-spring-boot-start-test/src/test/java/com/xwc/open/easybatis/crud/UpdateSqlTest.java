package com.xwc.open.easybatis.crud;

import com.xwc.open.easybatis.start.Application;
import com.xwc.open.easybatis.start.entity.User;
import com.xwc.open.easybatis.start.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/14
 * 描述：UpdateSqlTest
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UpdateSqlTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void update() {
        System.out.println("\n>>>>>>>>>>>>>>>>>>   update    <<<<<<<<<<<<<<<<<<<<");
        User user = userMapper.selectKey("37bd0225cc94400db744aac8dee8a004");
        userMapper.update(user);
        System.out.println("\n>>>>>>>>>>>>>>>>>>   updateActivate   <<<<<<<<<<<<<<<<<<<<");
        user.setName("吕布1");
        User activateUser = new User();
        activateUser.setId("\n37bd0225cc94400db744aac8dee8a004");
        activateUser.setName("吕布");
        userMapper.updateActivate(activateUser);
        System.out.println("\n>>>>>>>>>>>>>>>>>>    updateCode   <<<<<<<<<<<<<<<<<<<<");
        userMapper.updateCode("200");
        System.out.println("\n>>>>>>>>>>>>>>>>>>    updateCodeByName   <<<<<<<<<<<<<<<<<<<<");
        userMapper.updateCodeByName("200", "曹操");
    }
}
