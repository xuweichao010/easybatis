package com.xwc.open.easybatis.auditor;

import com.xwc.open.easybatis.start.Application;
import com.xwc.open.easybatis.start.entity.AuditorUser;
import com.xwc.open.easybatis.start.mapper.AuditorUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/23
 * 描述：
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class AuditorTest {
    @Autowired
    private AuditorUserMapper auditorUserMapper;

    @Test
    public void insert() {
        AuditorUser user = new AuditorUser();
        user.setId("123456790");
        user.setName("这是一个测试");
        user.setOrgName("测试组");
        user.setOrgCode("test");
        auditorUserMapper.insert(user);
    }

    @Test
    public void insertBatch() {
        AuditorUser user1 = new AuditorUser();
        user1.setId("123456781");
        user1.setName("这是一个测试1");
        user1.setOrgName("测试组");
        user1.setOrgCode("test");

        AuditorUser user2 = new AuditorUser();
        user2.setId("123456780");
        user2.setName("这是一个测试2");
        user2.setOrgName("测试组");
        user2.setOrgCode("test");
        auditorUserMapper.insertBatch(Arrays.asList(user1, user2));
    }

    @Test
    public void update(){
        AuditorUser auditorUser = auditorUserMapper.selectKey("123456790");
        auditorUser.setName("逻辑更新名字");
        auditorUserMapper.update(auditorUser);
    }
}
