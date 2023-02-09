package com.xwc.open.easybatis.sql.logic;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.LogicUser;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.LogicSourceGeneratorMapper;
import com.xwc.open.easybatis.model.NormalUserUpdateObject;
import com.xwc.open.easybatis.sql.fill.AnnotationFillAttribute;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 10:16
 */
public class MapperEasyAnnotationLogicUpdateBuilderTest {


    SqlSessionFactory sqlSessionFactory;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSession sqlSession;
    LogicSourceGeneratorMapper logicSourceGeneratorMapper;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.sqlSession = sqlSessionFactory.openSession();
        this.easyBatisConfiguration = new EasyBatisConfiguration(sqlSessionFactory.getConfiguration());
        this.easyBatisConfiguration.setMapUnderscoreToCamelCase(true);
        this.easyBatisConfiguration.addMapper(LogicSourceGeneratorMapper.class);
        this.logicSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(LogicSourceGeneratorMapper.class,
                sqlSession);
        logicSourceGeneratorMapper.delTestData();
        this.easyBatisConfiguration.addFillAttributeHandler(new AnnotationFillAttribute());
    }


    @Test
    public void logicUpdate() {
        LogicUser logicUser = create();
        logicUser.setName("logicUpdate");
        logicSourceGeneratorMapper.update(logicUser);
        NormalUser dbUser = logicSourceGeneratorMapper.findOne(logicUser.getId());
        Assert.assertEquals(logicUser.getName(), dbUser.getName());
    }

    @Test
    public void logicUpdateParam() {
        LogicUser logicUser = create();
        logicSourceGeneratorMapper.updateParam(logicUser.getId(), "logicUpdateParam");
        NormalUser dbUser = logicSourceGeneratorMapper.findOne(logicUser.getId());
        Assert.assertEquals("logicUpdateParam", dbUser.getName());

    }

    @Test
    public void logicUpdateObject() {
        LogicUser logicUser = create();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(logicUser.getId(), "logicUpdateObject", "", "31231");
        logicSourceGeneratorMapper.updateObject(updateObject);
        NormalUser dbUser = logicSourceGeneratorMapper.findOne(logicUser.getId());
        Assert.assertEquals("logicUpdateObject", dbUser.getName());
    }

    public LogicUser create() {
        LogicUser logicUser = LogicUser.randomUser();
        logicSourceGeneratorMapper.insert(logicUser);
        return logicUser;
    }

    @After
    public void after() {
        logicSourceGeneratorMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
