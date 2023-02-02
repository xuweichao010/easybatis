package com.xwc.open.easybatis.sql;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.GenericsBaseMapper;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.model.NormalUserDeleteObject;
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
public class MapperEasyAnnotationDeleteBuilderTest {


    SqlSessionFactory sqlSessionFactory;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSession sqlSession;
    SimpleSourceGeneratorMapper simpleSourceGeneratorMapper;
    GenericsBaseMapper genericsBaseMapper;

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
    }

    private NormalUser createUser() {
        NormalUser updateUser = NormalUser.randomUser();
        simpleSourceGeneratorMapper.insert(updateUser);
        return updateUser;
    }

    @Test
    public void simpleDel() {
        NormalUser delUser = createUser();
        simpleSourceGeneratorMapper.del(delUser.getId());
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(delUser.getId());
        Assert.assertNull(dbUser);
    }

    @Test
    public void simpleDynamicDel() {
        NormalUser delUser = createUser();
        simpleSourceGeneratorMapper.dynamicDel(delUser.getId(), null);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(delUser.getId());
        Assert.assertNull(dbUser);

        delUser = createUser();
        simpleSourceGeneratorMapper.dynamicDel(null, delUser.getName());
        dbUser = simpleSourceGeneratorMapper.findOne(delUser.getId());
        Assert.assertNull(dbUser);
    }

    @Test
    public void simpleDelObject() {
        NormalUser delUser = createUser();
        NormalUserDeleteObject deleteObject = NormalUserDeleteObject.convert(delUser.getId(), delUser.getName(), delUser.getOrgCode(), delUser.getOrgName());
        simpleSourceGeneratorMapper.delObject(deleteObject);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(delUser.getId());
        Assert.assertNull(dbUser);
    }

    @Test
    public void simpleDelDynamicObjectIgnore() {
        NormalUser delUser = createUser();
        NormalUserDeleteObject deleteObject = NormalUserDeleteObject.convert(delUser.getId(), null, null
                , delUser.getOrgName());
        simpleSourceGeneratorMapper.delDynamicObjectIgnore(null, deleteObject);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(delUser.getId());
        Assert.assertNull(dbUser);
    }


    @After
    public void after() {
        simpleSourceGeneratorMapper.delTestData();
        genericsBaseMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
