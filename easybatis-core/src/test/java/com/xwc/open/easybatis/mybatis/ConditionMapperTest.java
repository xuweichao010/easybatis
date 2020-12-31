package com.xwc.open.easybatis.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mybatis.condition.ConditionTableUser;
import com.xwc.open.easybatis.mybatis.condition.ConditionTableUserMapper;
import com.xwc.open.easybatis.mybatis.condition.ConditionUser;
import com.xwc.open.easybatis.mybatis.condition.ConditionUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/31
 * 描述：数据库操作测试
 */
public class ConditionMapperTest {


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

    @Test
    public void equal() {
        List<ConditionUser> conditionUserList = conditionUserMapper.defaultEqual("曹操");
        List<ConditionTableUser> conditionTableUserList = conditionTableUserMapper.defaultEqual("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList);
        conditionUserList = conditionUserMapper.defaultEqualDynamic("曹操");
        conditionTableUserList = conditionTableUserMapper.defaultEqualDynamic("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList);
        conditionUserList = conditionUserMapper.defaultEqualDynamic(null);
        conditionTableUserList = conditionTableUserMapper.defaultEqualDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList);
        conditionUserList = conditionUserMapper.equalAnnotation("曹操");
        conditionTableUserList = conditionTableUserMapper.equalAnnotation("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList);
        conditionUserList = conditionUserMapper.equalAnnotationDynamic("曹操");
        conditionTableUserList = conditionTableUserMapper.equalAnnotationDynamic("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList);
        conditionUserList = conditionUserMapper.equalAnnotationDynamic(null);
        conditionTableUserList = conditionTableUserMapper.equalAnnotationDynamic("t_user", null);
        validate(conditionUserList, conditionTableUserList);
        conditionUserList = conditionUserMapper.equalAnnotationCustomColumn("曹操");
        conditionTableUserList = conditionTableUserMapper.equalAnnotationCustomColumn("t_user", "曹操");
        validate(conditionUserList, conditionTableUserList);
    }


    private void validate(List<ConditionUser> userList, List<ConditionTableUser> tableUserList) {
        Assert.assertTrue(userList.stream().anyMatch(item -> "曹操".equals(item.getName())));
        Assert.assertTrue(tableUserList.stream().anyMatch(item -> "曹操".equals(item.getName())));
    }
}
