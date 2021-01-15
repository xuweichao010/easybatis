package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisTableUser;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisUser;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.UUID;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：简单的mysql测试
 */
public class DeleteMapperTest {


    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    MybatisTableUserMapper mybatisTableUserMapper;
    MybatisUserMapper mybatisUserMapper;
    SqlSession sqlSession;
    int TEST_TAG = 200;
    MybatisTableUser mybatisTableUser = genderMybatisTableUser();
    MybatisUser mybatisUser = genderMybatisUser();


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(MybatisTableUserMapper.class);
        easybatisConfiguration.addMapper(MybatisUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        mybatisTableUserMapper = sqlSession.getMapper(MybatisTableUserMapper.class);
        mybatisUserMapper = sqlSession.getMapper(MybatisUserMapper.class);
        mybatisTableUserMapper.clearTest("t_user", TEST_TAG);
        mybatisUserMapper.clearTest(TEST_TAG);
        mybatisTableUserMapper.insert("t_user", mybatisTableUser);
        mybatisUserMapper.insert(mybatisUser);
    }

    @After
    public void  after(){
        mybatisTableUserMapper.clearTest("t_user", TEST_TAG);
        mybatisUserMapper.clearTest(TEST_TAG);
    }

    @Test
    public void delParam() {
        Integer mybatisTableUserCount = mybatisTableUserMapper.deleteParam("t_user", mybatisTableUser.getName(), mybatisTableUser.getAge());
        Assert.assertEquals(mybatisTableUserCount, Integer.valueOf(1));
        Integer mybatisUserCount = mybatisUserMapper.deleteParam(mybatisUser.getName(), mybatisUser.getAge());
        Assert.assertEquals(mybatisUserCount, Integer.valueOf(1));
    }


    private MybatisUser genderMybatisUser() {
        Random random = new Random();
        MybatisUser tar = new MybatisUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        tar.setValid(TEST_TAG);
        return tar;
    }

    private MybatisTableUser genderMybatisTableUser() {
        Random random = new Random();
        MybatisTableUser tar = new MybatisTableUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        tar.setValid(TEST_TAG);
        return tar;
    }

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
