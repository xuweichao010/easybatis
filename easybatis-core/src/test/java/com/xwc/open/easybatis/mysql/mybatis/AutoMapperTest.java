package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mysql.mybatis.auto.AutoIdMapper;
import com.xwc.open.easybatis.mysql.mybatis.auto.AutoIdTableMapper;
import com.xwc.open.easybatis.mysql.mybatis.auto.AutoRole;
import com.xwc.open.easybatis.mysql.mybatis.auto.AutoTableRole;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisTableUser;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisUser;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisUserMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/3/25
 * 描述：自增长主键测试
 */
public class AutoMapperTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    AutoIdMapper autoIdMapper;
    AutoIdTableMapper autoIdTableMapper;
    SqlSession sqlSession;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(AutoIdTableMapper.class);
        easybatisConfiguration.addMapper(AutoIdMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        autoIdTableMapper = sqlSession.getMapper(AutoIdTableMapper.class);
        autoIdMapper = sqlSession.getMapper(AutoIdMapper.class);
    }

    @Test
    public void insert() {
        AutoRole autoRole = genderRole();
        autoIdMapper.insert(autoRole);
        Assert.assertNotNull(autoRole.getId());
        AutoTableRole autoTableRole = genderTableRole();
        autoIdTableMapper.insert("t_role", autoTableRole);
        Assert.assertNotNull(autoTableRole.getId());
    }

    @Test
    public void insertBatch(){
        List<AutoRole> autoRoles = Arrays.asList(genderRole(), genderRole());
        autoIdMapper.insertBatch(autoRoles);
        for (AutoRole autoRole : autoRoles) {
            Assert.assertNotNull(autoRole.getId());
        }
        List<AutoTableRole> autoTableRoles = Arrays.asList(genderTableRole(), genderTableRole());
        autoIdTableMapper.insertBatch("t_role",autoTableRoles);
        for (AutoTableRole autoTableRole : autoTableRoles) {
            Assert.assertNotNull(autoTableRole.getId());
        }
    }


    private AutoRole genderRole() {
        AutoRole tar = new AutoRole();
        tar.setName("角色");
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        return tar;
    }

    private AutoTableRole genderTableRole() {
        AutoTableRole tar = new AutoTableRole();
        tar.setName("角色");
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        return tar;
    }
}
