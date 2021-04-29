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
import org.junit.Assert;
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
        this.init();
    }

    @After
    public void after() {
        mixtureUserMapper.deleteByValid(INVALID);
        mixtureUserMapper.deleteByValid(VALID);
    }

    void init() {
        this.mixtureUser = this.genderMixtureUser();
        this.mixtureTableUser = this.genderMixtureTableUser();
        mixtureUserMapper.insert(mixtureUser);
        mixtureTableUserMapper.insert("t_user", mixtureTableUser);

    }

    @Test
    public void insert() {

    }

    @Test
    public void insertBatch() {
        List<MixtureTableUser> tableList = Arrays
                .asList(genderMixtureTableUser(), genderMixtureTableUser(), genderMixtureTableUser());
        List<MixtureUser> list = Arrays.asList(genderMixtureUser(), genderMixtureUser(), genderMixtureUser());
        mixtureTableUserMapper.insertBatch("t_user", tableList);
        mixtureUserMapper.insertBatch(list);
    }

    @Test
    public void selectKey() {

        Assert.assertNotNull(mixtureTableUser);
        MixtureUser mixtureUser = mixtureUserMapper.selectKey(this.mixtureUser.getId());
        Assert.assertNotNull(mixtureUser);
    }

    @Test
    public void update() {
        mixtureTableUserMapper.update("t_user", this.mixtureTableUser);
        mixtureUserMapper.update(this.mixtureUser);
    }

    @Test
    public void updateActive() {
        MixtureUser activeUser = new MixtureUser();
        activeUser.setAge(1);
        activeUser.setId(this.mixtureUser.getId());
        mixtureUserMapper.updateActivate(activeUser);

        MixtureTableUser activeTableUser = new MixtureTableUser();
        activeTableUser.setAge(1);
        activeTableUser.setId(this.mixtureTableUser.getId());
        mixtureTableUserMapper.updateActivate("t_user", activeTableUser);
    }

    @Test
    public void delete() {
        mixtureTableUserMapper.delete("t_user", this.mixtureTableUser.getId());
        mixtureUserMapper.delete(this.mixtureUser.getId());
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
