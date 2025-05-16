package cn.onetozero.easybatis.sql.logic;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.model.NormalUserDeleteObject;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.entity.LogicUser;
import cn.onetozero.easybatis.entity.NormalUser;
import cn.onetozero.easybatis.mapper.LogicSourceGeneratorMapper;
import cn.onetozero.easybatis.sql.fill.AnnotationFillAttribute;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/17 10:16
 */
public class MapperEasyAnnotationLogicDeleteBuilderTest {


    SqlSessionFactory sqlSessionFactory;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSession sqlSession;
    LogicSourceGeneratorMapper logicSourceGeneratorMapper;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        Environment environment = new SqlSessionFactoryBuilder().build(inputStream).getConfiguration().getEnvironment();
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
        this.easyBatisConfiguration.setEnvironment(environment);
        this.sqlSessionFactory = new DefaultSqlSessionFactory(this.easyBatisConfiguration);
        this.sqlSession = this.sqlSessionFactory.openSession();
        this.easyBatisConfiguration.addMapper(LogicSourceGeneratorMapper.class);
        this.logicSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(LogicSourceGeneratorMapper.class,
                sqlSession);
        logicSourceGeneratorMapper.delTestData();
        this.easyBatisConfiguration.addFillAttributeHandler(new AnnotationFillAttribute());
    }


    @Test
    public void logicDel() {
        LogicUser logicUser = create();
        logicSourceGeneratorMapper.del(logicUser.getId());
        NormalUser one = logicSourceGeneratorMapper.findOne(logicUser.getId());
        Assert.assertNull(one);
    }

    @Test
    public void logicDelObject() {
        LogicUser logicUser = create();
        logicSourceGeneratorMapper.delObject(NormalUserDeleteObject.convert(logicUser.getId(), logicUser.getName(),
                logicUser.getOrgCode(), logicUser.getOrgName()));
        NormalUser one = logicSourceGeneratorMapper.findOne(logicUser.getId());
        Assert.assertNull(one);
    }


    @Test
    public void logicDelDynamicObjectIgnore() {
        LogicUser logicUser = create();
        logicSourceGeneratorMapper.delDynamicObjectIgnore(null, NormalUserDeleteObject.convert(logicUser.getId(),
                logicUser.getName(),
                logicUser.getOrgCode(), logicUser.getOrgName()));
        NormalUser one = logicSourceGeneratorMapper.findOne(logicUser.getId());
        Assert.assertNull(one);
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
