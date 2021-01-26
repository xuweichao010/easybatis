package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.anno.auditor.Auditor;
import com.xwc.open.easybatis.mysql.mybatis.auditor.AuditorTableUser;
import com.xwc.open.easybatis.mysql.mybatis.auditor.AuditorTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.auditor.AuditorUser;
import com.xwc.open.easybatis.mysql.mybatis.auditor.AuditorUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicTableUser;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicUser;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicUserMapper;
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

public class AuditorMapperTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    AuditorTableUserMapper auditorTableUserMapper;
    AuditorUserMapper auditorUserMapper;
    SqlSession sqlSession;
    AuditorTableUser auditorTableUser;
    AuditorUser auditorUser;
    private final int VALID = 44;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(AuditorUserMapper.class);
        easybatisConfiguration.addMapper(AuditorTableUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        auditorUserMapper = sqlSession.getMapper(AuditorUserMapper.class);
        auditorTableUserMapper = sqlSession.getMapper(AuditorTableUserMapper.class);
        this.auditorTableUser = genderAuditorTableUser();
        auditorTableUserMapper.insert("t_user", auditorTableUser);
        this.auditorUser = genderAuditorUser();
        auditorUserMapper.insert(auditorUser);
    }

    @After
    public void after() {
        auditorUserMapper.deleteByValid(VALID);
    }

    @Test
    public void insert() {
        AuditorTableUser auditorTableUser = genderAuditorTableUser();
        auditorTableUserMapper.insert("t_user", auditorTableUser);
        AuditorUser auditorUser = genderAuditorUser();
        auditorUserMapper.insert(auditorUser);
    }

    @Test
    public void selectKey() {
        AuditorTableUser auditorTableUser = auditorTableUserMapper.selectKey("t_user", this.auditorTableUser.getId());
        Assert.assertNotNull(auditorTableUser);
        Assert.assertNotNull(auditorTableUser.getUpdateName());

        AuditorUser auditorUser = auditorUserMapper.selectKey(this.auditorUser.getId());
        Assert.assertNotNull(auditorUser);
        Assert.assertNotNull(auditorUser.getUpdateName());
    }


    private AuditorUser genderAuditorUser() {
        Random random = new Random();
        AuditorUser tar = new AuditorUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        tar.setValid(VALID);
        return tar;
    }

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private AuditorTableUser genderAuditorTableUser() {
        Random random = new Random();
        AuditorTableUser tar = new AuditorTableUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        tar.setValid(VALID);
        return tar;
    }
}
