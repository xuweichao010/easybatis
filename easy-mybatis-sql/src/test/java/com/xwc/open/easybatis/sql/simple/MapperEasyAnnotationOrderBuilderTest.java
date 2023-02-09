package com.xwc.open.easybatis.sql.simple;

import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.GenericsBaseMapper;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 类描述：单元测试
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:30
 */
public class MapperEasyAnnotationOrderBuilderTest {

    SqlSessionFactory sqlSessionFactory;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSession sqlSession;
    SimpleSourceGeneratorMapper simpleSourceGeneratorMapper;
    GenericsBaseMapper genericsBaseMapper;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        Environment environment = new SqlSessionFactoryBuilder().build(inputStream).getConfiguration().getEnvironment();
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
        this.easyBatisConfiguration.setEnvironment(environment);
        this.sqlSessionFactory = new DefaultSqlSessionFactory(this.easyBatisConfiguration);
        this.sqlSession = this.sqlSessionFactory.openSession();
        this.easyBatisConfiguration.addMapper(SimpleSourceGeneratorMapper.class);
        this.easyBatisConfiguration.addMapper(GenericsBaseMapper.class);
        this.simpleSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(SimpleSourceGeneratorMapper.class,
                sqlSession);
        this.genericsBaseMapper = this.easyBatisConfiguration.getMapper(GenericsBaseMapper.class, sqlSession);
    }

    @Test
    public void simpleMethodOrder() {
        List<NormalUser> order = simpleSourceGeneratorMapper.methodOrder();
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());
    }

    @Test
    public void simpleParamOrderAsc() {
        List<NormalUser> order = simpleSourceGeneratorMapper.orderDesc(true);
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());
    }

    @Test
    public void simpleDynamicOrderDesc() {
        List<NormalUser> order = simpleSourceGeneratorMapper.dynamicOrderDesc(true);
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());
    }

    @Test
    public void simpleOrderDescDynamic() {
        List<NormalUser> order = simpleSourceGeneratorMapper.orderDescDynamic(true);
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());
    }

    @Test
    public void simpleOrderAscDynamic() {
        List<NormalUser> order = simpleSourceGeneratorMapper.orderAscDynamic(true);
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());
    }

    @Test
    public void simpleOrderMixture() {
        List<NormalUser> order = simpleSourceGeneratorMapper.orderMixture(null, true, null);
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());

        order = simpleSourceGeneratorMapper.orderMixture(true, true, null);
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());

        order = simpleSourceGeneratorMapper.orderMixture(null, true, true);
        if (order.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, order.size());
    }


}
