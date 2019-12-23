package com.xwc.open.example;

import com.xwc.open.example.dao.UserEnumMapper;
import com.xwc.open.example.dao.UserMapper;
import com.xwc.open.example.entity.User;
import com.xwc.open.example.entity.UserEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 创建人：徐卫超
 * 时间：2019/12/20 17:38
 * 功能：枚举测试类
 * 备注：
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestEnum {

    @Autowired
    private UserEnumMapper userMapper;

    /**
     * 测试根据主键查询
     */
    @Test
    public void primaryKeySelect() {
        UserEnum user = userMapper.selectKey("37bd0225cc94400db744aac8dee8a001");
        System.out.println(user.toString());
        Assert.assertNotEquals(user, null);
    }

    @Test
    public void insertUUID() {
        UserEnum user = new UserEnum();
        user.setName("xwc");
        user.setValid(UserEnum.Valid.INVALID);
        Long count = userMapper.insert(user);
        Assert.assertEquals(count,new Long(1));
        userMapper.delete(user.getId());
    }

}
