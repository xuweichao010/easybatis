package com.xwc.open.easybatis.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mybatis.condition.ConditionTableUserMapper;
import com.xwc.open.easybatis.mybatis.condition.ConditionUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者：徐卫超 cc
 * 时间：2021/1/5
 * 描述：分组测试
 */
public class GroupByTest {
    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    ConditionTableUserMapper conditionTableUserMapper;
    ConditionUserMapper conditionUserMapper;
    SqlSession sqlSession;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(ConditionTableUserMapper.class);
        easybatisConfiguration.addMapper(ConditionUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        conditionTableUserMapper = sqlSession.getMapper(ConditionTableUserMapper.class);
        conditionUserMapper = sqlSession.getMapper(ConditionUserMapper.class);
    }

}
