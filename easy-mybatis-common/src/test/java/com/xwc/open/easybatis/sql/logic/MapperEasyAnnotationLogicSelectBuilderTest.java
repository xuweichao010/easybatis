package com.xwc.open.easybatis.sql.logic;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.LogicSourceGeneratorMapper;
import com.xwc.open.easybatis.model.NormalUserPageQueryDto;
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
import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 10:16
 */
public class MapperEasyAnnotationLogicSelectBuilderTest {


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
    public void logicFindOne() {
        NormalUser one = logicSourceGeneratorMapper.findOne("37bd0225cc94400db744aac8dee8a001");
        Assert.assertEquals("曹操", one.getName());
    }

    @Test
    public void logicFindOneDynamicIgnore() {
        NormalUser one = logicSourceGeneratorMapper.findOneDynamicIgnore(null, "37bd0225cc94400db744aac8dee8a001");
        Assert.assertEquals("曹操", one.getName());
    }

    @Test
    public void logicFindAll() {
        List<NormalUser> normalUsers = logicSourceGeneratorMapper.findAll();
        Assert.assertEquals(31, normalUsers.size());
    }

    @Test
    public void logicQueryObject() {
        List<NormalUser> normalUsers = logicSourceGeneratorMapper.queryObject(NormalUserPageQueryDto.createAllConditionQueryObject());
        Assert.assertEquals(1, normalUsers.size());
    }


    @After
    public void after() {
        logicSourceGeneratorMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
