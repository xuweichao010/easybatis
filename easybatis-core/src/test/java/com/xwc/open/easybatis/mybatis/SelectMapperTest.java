package com.xwc.open.easybatis.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mybatis.base.MybatisTableUser;
import com.xwc.open.easybatis.mybatis.base.MybatisTableUserMapper;
import com.xwc.open.easybatis.mybatis.base.MybatisUser;
import com.xwc.open.easybatis.mybatis.base.MybatisUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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
    public void methodParamDynamic(){
        List<MybatisUser> userList = mybatisUserMapper.methodParamDynamic("上海分公司1", "200003");
        userList = mybatisUserMapper.methodParamDynamic(null, null);
        userList = mybatisUserMapper.methodParamDynamic(null, "200003");
        userList = mybatisUserMapper.methodParamDynamic("上海分公司1", null);
        List<MybatisTableUser> tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", "总公司", "200003");
        tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", null, null);
        tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", null, "200003");
        tableUserList = mybatisTableUserMapper.methodParamDynamic("t_user", "上海分公司1", null);

    }




}
