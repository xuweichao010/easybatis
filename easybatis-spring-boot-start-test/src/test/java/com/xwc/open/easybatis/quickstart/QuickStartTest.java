package com.xwc.open.easybatis.quickstart;

import com.xwc.open.easybatis.start.Application;
import com.xwc.open.easybatis.start.entity.User;
import com.xwc.open.easybatis.start.mapper.UserMapper;
import org.apache.ibatis.annotations.Delete;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/12
 * 描述：快速開始
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QuickStartTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void select() {
        User user = userMapper.selectKey("37bd0225cc94400db744aac8dee8a001");
        System.out.println(user);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setId("123456789");
        user.setName("这是一个测试");
        user.setOrgName("测试组");
        user.setOrgCode("test");
        userMapper.insert(user);
    }

    @Test
    public void insertBatch() {
        User user1 = new User();
        user1.setId("123456781");
        user1.setName("这是一个测试1");
        user1.setOrgName("测试组");
        user1.setOrgCode("test");

        User user2 = new User();
        user2.setId("123456780");
        user2.setName("这是一个测试2");
        user2.setOrgName("测试组");
        user2.setOrgCode("test");
        userMapper.insertBatch(Arrays.asList(user1, user2));
    }

    @Test
    public void update() {
        User user = userMapper.selectKey("123456789");
        user.setName("这是一个测试更新");
        userMapper.update(user);
    }

    @Test
    public void updateActivate() {
        User user = new User();
        user.setId("123456789");
        user.setName("这是一个测试更新1");
        userMapper.updateActivate(user);
    }

    @Test
    public void del(){
        userMapper.delete("12345678");
    }
}
