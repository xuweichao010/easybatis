package com.xwc;

import com.alibaba.fastjson.JSONObject;
import com.xwc.dao.OrgMapper;
import com.xwc.dao.UserMapper;
import com.xwc.entity.Org;
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
 * 业务：
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TestBaseMapper {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrgMapper orgMapper;

    /**
     * 测试插入数据
     * 在维护主键的时候，需要注意一个问题，就是可能会用到联合主键，一般我们用联合主键的时候，谁去关联就把primaryKey加载维护的ID上
     */
    @Test
    public void testInsert() {
        //测试自定义类型的插入
        Org org = new Org();
        org.setParentName("");
        org.setParentCode("0");
        org.setCode("201JV7");
        org.setName("总部");
        orgMapper.insert(org);
        System.out.println(JSONObject.toJSONString(org));

        //测试自增类型的插入
        User user = new User();
        user.setOrgCode("201JV7");
        user.setOrgName("总部");
        user.setAccount("13147197616");
        user.setName("张飞");
        user.setPassword("123456");
        userMapper.insert(user);
        System.out.println(JSONObject.toJSONString(user));
        if (user.getId() > 0) System.out.println("有主键");
    }

    /**
     * 测试批量插入
     */
    @Test
    public void testInsertBatch() {
        ArrayList<Org> orgList = new ArrayList<>();
        //测试自定义类型的插入
        Org org = new Org();
        org.setParentName("");
        org.setParentCode("0");
        org.setCode("201JV71");
        org.setName("总部");
        orgList.add(org);
        orgMapper.insertBatch(orgList);
        System.out.println(JSONObject.toJSONString(org));


        //测试自增类型的插入
        ArrayList<User> userList = new ArrayList<>();
        User user = new User();
        user.setOrgCode("201JV7");
        user.setOrgName("总部");
        user.setAccount("13147197616");
        user.setName("张飞");
        user.setPassword("123456");
        userList.add(user);
        userMapper.insertBatch(userList);
        System.out.println(JSONObject.toJSONString(user));
        if (user.getId() > 0) System.out.println("有主键");
    }

    //根绝parimaryKey查询
    @Test
    public void testSelectKey() {
        User user = userMapper.selectKey(1L);
        System.out.println(JSONObject.toJSONString(user));
    }

    //删除测试
    @Test
    public void testDelKey() {
        long delete = userMapper.delete(1L);
        System.out.println(delete);
    }


}
