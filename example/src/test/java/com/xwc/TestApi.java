package com.xwc;

import com.alibaba.fastjson.JSONObject;
import com.xwc.dao.BaseUserMapper;
import com.xwc.dao.UserMapper;
import com.xwc.entity.User;
import com.xwc.entity.UserQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2018/8/31  16:51
 * 功能：
 * 业务：
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestApi {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BaseUserMapper baseUserMapper;

    @Test
    public void testInsert() {
//        User user = new User();
//        user.setPassword("1111");
//        user.setAccount("1111");
//        baseUserMapper.insert(user);
//        System.out.println(JSONObject.toJSONString(user));
        List<User> users = baseUserMapper.byName("111");
        System.out.println(JSONObject.toJSONString(users));
    }

}
