package com.xwc.open.easybatis.sql.logic;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.LogicUser;
import com.xwc.open.easybatis.mapper.LogicSourceGeneratorMapper;
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
import java.util.Collections;
import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 10:16
 */
public class MapperEasyAnnotationLogicInsertBuilderTest {


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
    public void logicInsert() {
        LogicUser logicUser = LogicUser.randomUser();
        int insert = logicSourceGeneratorMapper.insert(logicUser);
        Assert.assertEquals(1, logicUser.getValid());
    }

    @Test
    public void logicInsertBatchIgnore() {
        List<LogicUser> logicUsers = Collections.singletonList(LogicUser.randomUser());
        logicSourceGeneratorMapper.insertBatchIgnore(null, logicUsers);
        for (LogicUser logicUser : logicUsers) {
            Assert.assertEquals(1, logicUser.getValid());
        }
    }

    @Test
    public void logicInsertBatch() {
        List<LogicUser> logicUsers = Collections.singletonList(LogicUser.randomUser());
        logicSourceGeneratorMapper.insertBatch(logicUsers);
        for (LogicUser logicUser : logicUsers) {
            Assert.assertEquals(1, logicUser.getValid());
        }
    }


    @After
    public void after() {
        logicSourceGeneratorMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
