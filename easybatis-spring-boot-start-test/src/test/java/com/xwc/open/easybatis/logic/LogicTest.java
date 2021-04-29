package com.xwc.open.easybatis.logic;

import com.xwc.open.easybatis.start.Application;
import com.xwc.open.easybatis.start.entity.LogicUser;
import com.xwc.open.easybatis.start.entity.User;
import com.xwc.open.easybatis.start.mapper.LogicUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/21
 * 描述：逻辑删除演示
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class LogicTest {

    @Autowired
    private LogicUserMapper logicUserMapper;

    @Test
    public void selectKey() {
        logicUserMapper.selectKey("37bd0225cc94400db744aac8dee8a004");
    }

    @Test
    public void insert() {
        LogicUser user = new LogicUser();
        user.setId("123456781");
        user.setName("这是一个测试");
        user.setOrgName("测试组");
        user.setOrgCode("test");
        logicUserMapper.insert(user);
        System.out.println(user);
    }

    @Test
    public void update() {
        LogicUser user = logicUserMapper.selectKey("123456781");
        user.setName("这是一个测试更新");
        logicUserMapper.update(user);
    }
    @Test
    public void del(){
        logicUserMapper.delete("123456781");
    }

}
