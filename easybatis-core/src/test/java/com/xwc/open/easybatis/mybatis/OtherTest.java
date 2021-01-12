package com.xwc.open.easybatis.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mybatis.other.OtherTableUserMapper;
import com.xwc.open.easybatis.mybatis.other.OtherUser;
import com.xwc.open.easybatis.mybatis.other.OtherUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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
        List<OtherUser> otherUsers = otherUserMapper.orderBy(null);
        System.out.println(otherUsers);
    }

    @Test
    public void Count() {
        Integer count = otherUserMapper.count();
        System.out.println(count);
    }

    @Test
    public void page() {

    }

    @Test
    public void distinct() {
        List<Integer> integers = otherUserMapper.distinctAge();
        System.out.println(integers);
    }


}
