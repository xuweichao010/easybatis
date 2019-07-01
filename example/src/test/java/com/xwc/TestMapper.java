package com.xwc;

import com.alibaba.fastjson.JSONObject;
import com.xwc.dao.OrgMapper;
import com.xwc.dao.UserMapper;
import com.xwc.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

/**
 * 创建人：徐卫超
 * 创建时间：2018/8/31  16:51
 * 功能：
 * 业务：测试简单的增删改查吧
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestMapper {

    @Autowired
    private UserMapper userMapper;


    @Test
    public void testInsert() {
        User user = new User();
        userMapper.updateBase(10,"徐卫超");
        System.out.println(JSONObject.toJSONString(user));
    }




}
