package com.xwc.open.easybatis.sql.simple;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.GenericsBaseMapper;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.model.QueryInObject;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 类描述：单元测试
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:30
 */
public class MapperEasyAnnotationConditionBuilderTest {

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
    }

    @Test
    public void simpleFindOne() {
        NormalUser normalUser = simpleSourceGeneratorMapper.findOne("37bd0225cc94400db744aac8dee8a001");
        if (normalUser == null) {
            Assert.fail();
        }
        Assert.assertEquals("曹操", normalUser.getName());
    }

    @Test
    public void simpleFindOneDynamic() {
        NormalUser normalUser = simpleSourceGeneratorMapper.findOneDynamic("37bd0225cc94400db744aac8dee8a001");
        if (normalUser == null) {
            Assert.fail();
        }
        Assert.assertEquals("曹操", normalUser.getName());
    }

    @Test
    public void simpleFindOneIgnore() {
        NormalUser normalUser = simpleSourceGeneratorMapper.findOneIgnore(null, "37bd0225cc94400db744aac8dee8a001");
        if (normalUser == null) {
            Assert.fail();
        }
        Assert.assertEquals("曹操", normalUser.getName());
    }

    @Test
    public void simpleFindOneDynamicIgnore() {
        NormalUser normalUser = simpleSourceGeneratorMapper.findOneDynamicIgnore(null, "37bd0225cc94400db744aac8dee8a001");
        if (normalUser == null) {
            Assert.fail();
        }
        Assert.assertEquals("曹操", normalUser.getName());
    }

    @Test
    public void genericsSelectKey() {
        NormalUser normalUser = genericsBaseMapper.selectKey("37bd0225cc94400db744aac8dee8a001");
        if (normalUser == null) {
            Assert.fail();
        }
        Assert.assertEquals("曹操", normalUser.getName());
    }

    @Test
    public void genericsSelectKeyIgnore() {
        NormalUser normalUser = genericsBaseMapper.selectKeyIgnore(null, "37bd0225cc94400db744aac8dee8a001");
        if (normalUser == null) {
            Assert.fail();
        }
        Assert.assertEquals("曹操", normalUser.getName());
    }

    @Test
    public void simpleBetween() {
        List<NormalUser> betweenAge = simpleSourceGeneratorMapper.between(20, 40);
        if (betweenAge.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(28, betweenAge.size());
    }

    @Test
    public void simpleBetweenIgnore() {
        List<NormalUser> betweenAge = simpleSourceGeneratorMapper.betweenIgnore(null, 20, 40);
        if (betweenAge.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(28, betweenAge.size());
    }

    @Test
    public void simpleBetweenDynamic() {
        List<NormalUser> betweenAge = simpleSourceGeneratorMapper.betweenDynamic(20, null);
        if (betweenAge.isEmpty()) {
            Assert.fail();
        }
        Assert.assertEquals(31, betweenAge.size());
    }

    @Test
    public void simpleIn() {
        List<String> ids = Collections.singletonList("37bd0225cc94400db744aac8dee8a001");
        List<NormalUser> in = simpleSourceGeneratorMapper.in(ids);
        NormalUser normalUser = in.stream().filter(item -> Objects.equals("曹操", item.getName()))
                .findAny().orElse(null);
        if (normalUser == null) {
            Assert.fail();
        }
    }


    @Test
    public void simpleInIgnore() {
        List<String> ids = Collections.singletonList("37bd0225cc94400db744aac8dee8a001");
        List<NormalUser> in = simpleSourceGeneratorMapper.inIgnore(null, ids);
        NormalUser normalUser = in.stream().filter(item -> Objects.equals("曹操", item.getName()))
                .findAny().orElse(null);
        if (normalUser == null) {
            Assert.fail();
        }
    }

    @Test
    public void simpleInObject() {
        QueryInObject queryInObject = new QueryInObject();
        queryInObject.setId("37bd0225cc94400db744aac8dee8a001");
        queryInObject.setAges(Arrays.asList(50, 51, 52));
        List<NormalUser> in = simpleSourceGeneratorMapper.inObject(queryInObject);
        NormalUser normalUser = in.stream().filter(item -> Objects.equals("曹操", item.getName()))
                .findAny().orElse(null);
        if (normalUser == null) {
            Assert.fail();
        }
    }

}