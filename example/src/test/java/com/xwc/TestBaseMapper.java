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
public class TestBaseMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrgMapper orgMapper;

    @Test
    public void testInsert() {
        //测试自增类型的插入
        User user = new User();
        user.setAccount("2312312");
        user.setOrgCode("201JV7");
        user.setOrgName("总部");
        user.setAccount("13147197616");
        user.setName("张飞");
        user.setPassword("123456");
        userMapper.insert(user);
        System.out.println(JSONObject.toJSONString(user));
    }


    @Test
    //我们把插入的ID拿过来查询一下看是否是正常查询的
    public void testSelectKey() {
        User user = userMapper.selectKey(17L);
        System.out.println(JSONObject.toJSONString(user));
    }

    @Test
    public void testUpdate() {
        //我们来更新一下用户的属性，为了简单我们把用户的属性全部设置为null
        User user = userMapper.selectKey(9L);
        user.setAccount("1111");
        user.setPassword("");
        userMapper.update(user);

        System.out.println(JSONObject.toJSONString(user));
    }

    @Test
    public void testUpdateEnhance(){
    }

    //删除测试
    @Test
    public void testDelKey() {
        //最后我们把数据删除掉吧
        long delete = userMapper.delete(6L);
        System.out.println(delete);
    }

    /**
     * 后面参考自定查询吧 TestCustomSelect.class
     */
    @Test
    public void testInsertBatch() {
        User user = new User();
        ArrayList<User> list = new ArrayList<>();
        list.add(user);
        list.add(user);
        list.add(user);
        userMapper.insertBatch(list);
    }


}
