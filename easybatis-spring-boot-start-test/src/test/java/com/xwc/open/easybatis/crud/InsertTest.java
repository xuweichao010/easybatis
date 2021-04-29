package com.xwc.open.easybatis.crud;

import com.xwc.open.easybatis.start.Application;
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
public class InsertTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {

    }
}
