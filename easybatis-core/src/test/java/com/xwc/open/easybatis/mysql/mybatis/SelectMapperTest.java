package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mysql.mybatis.base.*;
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
 * 时间：2020/12/17
 * 描述：简单的mysql测试
 */
public class SelectMapperTest {



    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    MybatisTableUserMapper mybatisTableUserMapper;
    MybatisUserMapper mybatisUserMapper;
    SqlSession sqlSession;
    int TEST_TAG = 300;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(MybatisTableUserMapper.class);
        easybatisConfiguration.addMapper(MybatisUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        mybatisTableUserMapper = sqlSession.getMapper(MybatisTableUserMapper.class);
        mybatisUserMapper = sqlSession.getMapper(MybatisUserMapper.class);
        mybatisTableUserMapper.clearTest("t_user", TEST_TAG);
        mybatisUserMapper.clearTest(TEST_TAG);
    }


    @Test
    public void methodGlobalDynamic() {
        List<MybatisUser> userList = mybatisUserMapper.methodGlobalDynamic("上海分公司1", "200003");
        userList = mybatisUserMapper.methodGlobalDynamic(null, null);
        userList = mybatisUserMapper.methodGlobalDynamic(null, "200003");
        userList = mybatisUserMapper.methodGlobalDynamic("上海分公司1", null);
        List<MybatisTableUser> tableUserList = mybatisTableUserMapper.methodGlobalDynamic("t_user", "总公司", "200003");
        tableUserList = mybatisTableUserMapper.methodGlobalDynamic("t_user", null, null);
        tableUserList = mybatisTableUserMapper.methodGlobalDynamic("t_user", null, "200003");
        tableUserList = mybatisTableUserMapper.methodGlobalDynamic("t_user", "上海分公司1", null);
    }

    @Test
    public void methodParamDynamic() {
        List<MybatisUser> userList = mybatisUserMapper.methodParamDynamic("总公司", "200");
        List<MybatisTableUser> tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", "总公司", "200");
        validate(userList,tableUserList);
        userList = mybatisUserMapper.methodParamDynamic(null, null);
        tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", null, null);
        validate(userList,tableUserList);
        userList = mybatisUserMapper.methodParamDynamic(null, "200");
        tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", null, "200");
        validate(userList,tableUserList);
        userList = mybatisUserMapper.methodParamDynamic("总公司", null);
        tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", "总公司", null);
        validate(userList,tableUserList);
    }

    @Test
    public void methodCustom() {
        MybatisUserOne filter = new MybatisUserOne();
        List<MybatisUser> userList = mybatisUserMapper.methodCustom(filter);
        List<MybatisTableUser> tableUserList = mybatisTableUserMapper.methodCustom("t_user", filter);
        validate(userList, tableUserList);
        filter.setOrgCode("200");
        userList = mybatisUserMapper.methodCustom(filter);
        tableUserList = mybatisTableUserMapper.methodCustom("t_user", filter);
        validate(userList, tableUserList);
        filter.setOrgName("总公司");
        userList = mybatisUserMapper.methodCustom(filter);
        tableUserList = mybatisTableUserMapper.methodCustom("t_user", filter);
        validate(userList, tableUserList);
        filter.setId("37bd0225cc94400db744aac8dee8a001");
        userList = mybatisUserMapper.methodCustom(filter);
        tableUserList = mybatisTableUserMapper.methodCustom("t_user", filter);
        validate(userList, tableUserList);
    }

    @Test
    public void methodMultiCustom() {
        MybatisUserOne filter = new MybatisUserOne();
        MybatisUserTwo filter1 = new MybatisUserTwo();
        List<MybatisUser> userList = mybatisUserMapper.methodMultiCustom(filter, filter1);
        List<MybatisTableUser> tableUserList = mybatisTableUserMapper.methodMultiCustom("t_user", filter, filter1);
        validate(userList, tableUserList);
        filter.setOrgCode("200");
        filter1.setJob(1);
        userList = mybatisUserMapper.methodMultiCustom(filter, filter1);
        tableUserList = mybatisTableUserMapper.methodMultiCustom("t_user", filter, filter1);
        validate(userList, tableUserList);
        filter.setOrgName("总公司");
        filter1.setValid(1);
        userList = mybatisUserMapper.methodMultiCustom(filter, filter1);
        tableUserList = mybatisTableUserMapper.methodMultiCustom("t_user", filter, filter1);
        validate(userList, tableUserList);
        filter.setId("37bd0225cc94400db744aac8dee8a001");
        filter1.setName("曹操");
        userList = mybatisUserMapper.methodMultiCustom(filter, filter1);
        tableUserList = mybatisTableUserMapper.methodMultiCustom("t_user", filter, filter1);
        validate(userList, tableUserList);
    }

    @Test
    public void methodMixture() {
        MybatisUserTwo filter = new MybatisUserTwo();
        List<MybatisUser> userList = mybatisUserMapper.methodMixture(null, "200", filter);
        List<MybatisTableUser> tableUserList = mybatisTableUserMapper.methodMixture("t_user", null, "200", filter);
        validate(userList, tableUserList);
        filter.setValid(1);
        userList = mybatisUserMapper.methodMixture(null, "200", filter);
        tableUserList = mybatisTableUserMapper.methodMixture("t_user", null, "200", filter);
        validate(userList, tableUserList);
        filter.setAge(50);
        userList = mybatisUserMapper.methodMixture("曹操", "200", filter);
        tableUserList = mybatisTableUserMapper.methodMixture("t_user", "曹操", "200", filter);
        validate(userList, tableUserList);
    }


    private void validate(List<MybatisUser> userList, List<MybatisTableUser> tableUserList) {
        Assert.assertTrue(userList.stream().anyMatch(item -> "曹操".equals(item.getName())));
        Assert.assertTrue(tableUserList.stream().anyMatch(item -> "曹操".equals(item.getName())));
    }


}
