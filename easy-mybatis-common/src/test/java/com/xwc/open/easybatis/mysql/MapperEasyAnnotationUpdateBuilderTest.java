package com.xwc.open.easybatis.mysql;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.GenericsBaseMapper;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
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
public class MapperEasyAnnotationUpdateBuilderTest {


    SqlSessionFactory sqlSessionFactory;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSession sqlSession;
    SimpleSourceGeneratorMapper simpleSourceGeneratorMapper;
    GenericsBaseMapper genericsBaseMapper;
    NormalUser updateUser = NormalUser.randomUser();

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.sqlSession = sqlSessionFactory.openSession();
        this.easyBatisConfiguration = new EasyBatisConfiguration(sqlSessionFactory.getConfiguration());
        this.easyBatisConfiguration.setMapUnderscoreToCamelCase(true);
        this.easyBatisConfiguration.addMapper(SimpleSourceGeneratorMapper.class);
        this.easyBatisConfiguration.addMapper(GenericsBaseMapper.class);
        this.simpleSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(SimpleSourceGeneratorMapper.class,
                sqlSession);
        this.genericsBaseMapper = this.easyBatisConfiguration.getMapper(GenericsBaseMapper.class, sqlSession);
        simpleSourceGeneratorMapper.delTestData();
        genericsBaseMapper.delTestData();
        simpleSourceGeneratorMapper.insert(updateUser);

    }

    @Test
    public void simpleUpdate() {
        updateUser.setName("simpleUpdate");
        simpleSourceGeneratorMapper.update(updateUser);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(updateUser.getName(), dbUser.getName());
    }

    @Test
    public void simpleUpdateDynamic() {
        NormalUser updateDynamic = new NormalUser();
        updateDynamic.setName("simpleUpdateDynamic");
        updateDynamic.setId(updateUser.getId());
        simpleSourceGeneratorMapper.updateDynamic(updateDynamic);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(updateDynamic.getName(), dbUser.getName());
        Assert.assertEquals(dbUser.getOrgName(), updateUser.getOrgName());
    }

    @Test
    public void simpleUpdateParam() {
        simpleSourceGeneratorMapper.updateParam(updateUser.getId(), "simpleUpdateParam");
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals("simpleUpdateParam", dbUser.getName());
    }


    @Test
    public void simpleUpdateParamDynamic() {
        simpleSourceGeneratorMapper.updateParamDynamic(updateUser.getId(), "simpleUpdateParamDynamic", null);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals("simpleUpdateParamDynamic", dbUser.getName());
        Assert.assertEquals(dbUser.getAge(), updateUser.getAge());
    }

    @Test
    public void simpleDynamicUpdateParam() {
        simpleSourceGeneratorMapper.updateParamDynamic(updateUser.getId(), null, 100);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(100, (int) dbUser.getAge());
        Assert.assertEquals(dbUser.getName(), updateUser.getName());
    }


    @After
    public void after() {
        simpleSourceGeneratorMapper.delTestData();
        genericsBaseMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
