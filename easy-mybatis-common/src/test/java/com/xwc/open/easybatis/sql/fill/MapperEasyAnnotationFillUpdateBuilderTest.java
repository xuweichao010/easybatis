package com.xwc.open.easybatis.sql.fill;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.FillUser;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.FillSourceGeneratorMapper;
import com.xwc.open.easybatis.mapper.GenericsBaseMapper;
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
public class MapperEasyAnnotationFillUpdateBuilderTest {


    SqlSessionFactory sqlSessionFactory;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSession sqlSession;
    FillSourceGeneratorMapper fillSourceGeneratorMapper;
    GenericsBaseMapper genericsBaseMapper;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.sqlSession = sqlSessionFactory.openSession();
        this.easyBatisConfiguration = new EasyBatisConfiguration(sqlSessionFactory.getConfiguration());
        this.easyBatisConfiguration.setMapUnderscoreToCamelCase(true);
        this.easyBatisConfiguration.addMapper(FillSourceGeneratorMapper.class);
        this.easyBatisConfiguration.addMapper(GenericsBaseMapper.class);
        this.fillSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(FillSourceGeneratorMapper.class,
                sqlSession);
        this.genericsBaseMapper = this.easyBatisConfiguration.getMapper(GenericsBaseMapper.class, sqlSession);
        fillSourceGeneratorMapper.delTestData();
        this.easyBatisConfiguration.addFillAttributeHandler(new AnnotationFillAttribute());
        genericsBaseMapper.delTestData();
    }

    @Test
    public void fillUpdate() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.update(fillUser);
        NormalUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals(fillUser.getUpdateName(), dbUser.getUpdateName());
        Assert.assertEquals(fillUser.getUpdateId(), dbUser.getUpdateId());


    }

    @Test
    public void fillUpdateDynamic() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.updateDynamic(fillUser);
        NormalUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals(fillUser.getUpdateName(), dbUser.getUpdateName());
        Assert.assertEquals(fillUser.getUpdateId(), dbUser.getUpdateId());
    }


    @Test
    public void fillUpdateParam() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.updateParam(fillUser.getId(), fillUser.getName());
        NormalUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());
    }

    private FillUser createUser() {
        FillUser fillUser = FillUser.randomUser();
        fillSourceGeneratorMapper.insert(fillUser);
        return fillUser;
    }


    @After
    public void after() {
        fillSourceGeneratorMapper.delTestData();
        genericsBaseMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
