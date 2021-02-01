package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mysql.mybatis.mixture.MixtureTableUser;
import com.xwc.open.easybatis.mysql.mybatis.mixture.MixtureTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.mixture.MixtureUser;
import com.xwc.open.easybatis.mysql.mybatis.mixture.MixtureUserMapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class MixtureMapperTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    MixtureTableUserMapper mixtureTableUserMapper;
    MixtureUserMapper mixtureUserMapper;
    SqlSession sqlSession;
    MixtureUser mixtureUser;
    MixtureTableUser mixtureTableUser;
    private final static int VALID = 200;
    private final static int INVALID = 201;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(MixtureTableUserMapper.class);
        easybatisConfiguration.addMapper(MixtureUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        mixtureUserMapper = sqlSession.getMapper(MixtureUserMapper.class);
        mixtureTableUserMapper = sqlSession.getMapper(MixtureTableUserMapper.class);
        init();
    }

    @After
    public void after() {
        mixtureUserMapper.deleteByValid(INVALID);
        mixtureUserMapper.deleteByValid(VALID);
    }

    void init() {
        List<MixtureTableUser> tableList = Arrays
                .asList(genderMixtureTableUser(), genderMixtureTableUser(), genderMixtureTableUser());
        List<MixtureUser> list = Arrays.asList(genderMixtureUser(), genderMixtureUser(), genderMixtureUser());
        mixtureTableUserMapper.insertBatch("t_user", tableList);
        mixtureUserMapper.insertBatch(list);
    }

    @Test
    public void selectKey() {

    }

    private MixtureUser genderMixtureUser() {
        Random random = new Random();
        MixtureUser tar = new MixtureUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        return tar;
    }

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private MixtureTableUser genderMixtureTableUser() {
        Random random = new Random();
        MixtureTableUser tar = new MixtureTableUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        return tar;
    }
}
