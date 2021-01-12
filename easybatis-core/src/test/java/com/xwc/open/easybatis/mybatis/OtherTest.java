package com.xwc.open.easybatis.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mybatis.other.OtherTableUser;
import com.xwc.open.easybatis.mybatis.other.OtherTableUserMapper;
import com.xwc.open.easybatis.mybatis.other.OtherUser;
import com.xwc.open.easybatis.mybatis.other.OtherUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2021/1/5
 * 描述：分组测试
 */
public class OtherTest {
    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    OtherTableUserMapper otherTableUserMapper;
    OtherUserMapper otherUserMapper;
    SqlSession sqlSession;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(OtherTableUserMapper.class);
        easybatisConfiguration.addMapper(OtherUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        otherUserMapper = sqlSession.getMapper(OtherUserMapper.class);
        otherTableUserMapper = sqlSession.getMapper(OtherTableUserMapper.class);
    }

    @Test
    public void orderBy() {
        validateOrderDesc0(otherUserMapper.orderByDesc(null));
        validateOrderDesc(otherTableUserMapper.orderByDesc("t_user", null));
        validateOrderAsc0(otherUserMapper.orderByAsc(null));
        validateOrderAsc(otherTableUserMapper.orderByAsc("t_user", null));
    }

    @Test
    public void count() {
        Integer count = otherUserMapper.count();
        Assert.assertEquals(count, Integer.valueOf(31));
    }


    @Test
    public void page() {

    }

    @Test
    public void distinct() {
        List<Integer> integers = otherUserMapper.distinctAge();
        Integer count = otherUserMapper.count();
        if (integers.size() == count) {
            Assert.fail();
        }
    }


    private void validateOrderDesc0(List<OtherUser> list) {
        for (int i = 0; i < list.size(); i++) {
            OtherUser before = list.get(i - 1 < 0 ? 0 : i);
            OtherUser current = list.get(i);
            if (before.getAge() < current.getAge()) {
                Assert.fail();
            }
        }
    }

    private void validateOrderAsc0(List<OtherUser> list) {
        for (int i = 0; i < list.size(); i++) {
            OtherUser before = list.get(i - 1 < 0 ? 0 : i);
            OtherUser current = list.get(i);
            if (before.getAge() > current.getAge()) {
                Assert.fail();
            }
        }
    }

    private void validateOrderDesc(List<OtherTableUser> list) {
        for (int i = 0; i < list.size(); i++) {
            OtherTableUser before = list.get(i - 1 < 0 ? 0 : i);
            OtherTableUser current = list.get(i);
            if (before.getAge() < current.getAge()) {
                Assert.fail();
            }
        }
    }

    private void validateOrderAsc(List<OtherTableUser> list) {
        for (int i = 0; i < list.size(); i++) {
            OtherTableUser before = list.get(i - 1 < 0 ? 0 : i);
            OtherTableUser current = list.get(i);
            if (before.getAge() > current.getAge()) {
                Assert.fail();
            }
        }
    }


}
