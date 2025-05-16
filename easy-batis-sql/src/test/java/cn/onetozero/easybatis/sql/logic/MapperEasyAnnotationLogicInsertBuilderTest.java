package cn.onetozero.easybatis.sql.logic;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.entity.LogicUser;
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
import java.util.Collections;
import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/17 10:16
 */
public class MapperEasyAnnotationLogicInsertBuilderTest {


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
