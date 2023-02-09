package com.xwc.open.easybatis.sql.simple;

import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.entity.NormalUser;
import com.xwc.open.easybatis.mapper.GenericsBaseMapper;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 10:16
 */
public class MapperEasyAnnotationInsertBuilderTest {


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
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
        this.easyBatisConfiguration.setMapUnderscoreToCamelCase(true);
        this.easyBatisConfiguration.addMapper(SimpleSourceGeneratorMapper.class);
        this.easyBatisConfiguration.addMapper(GenericsBaseMapper.class);
        this.simpleSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(SimpleSourceGeneratorMapper.class,
                sqlSession);
        this.genericsBaseMapper = this.easyBatisConfiguration.getMapper(GenericsBaseMapper.class, sqlSession);
        simpleSourceGeneratorMapper.delTestData();
        genericsBaseMapper.delTestData();

    }

    @Test
    public void simpleInsert() {
        simpleSourceGeneratorMapper.insert(NormalUser.randomUser());
    }

    @Test
    public void simpleInsertIgnore() {
        simpleSourceGeneratorMapper.insertIgnore("", NormalUser.randomUser());
    }

    @Test
    public void simpleInsertBatch() {
        simpleSourceGeneratorMapper.insertBatch(Arrays.asList(NormalUser.randomUser(), NormalUser.randomUser()));
    }

    @Test
    public void simpleInsertBatchIgnore() {
        simpleSourceGeneratorMapper.insertBatchIgnore("", Arrays.asList(NormalUser.randomUser(), NormalUser.randomUser()));
    }

    @Test
    public void genericsInsert() {
        genericsBaseMapper.insert(NormalUser.randomUser());
    }

    @Test
    public void genericsInsertIgnore() {
        genericsBaseMapper.insertIgnore("", NormalUser.randomUser());
    }

    @Test
    public void genericsInsertBatch() {
        genericsBaseMapper.insertBatch(Arrays.asList(NormalUser.randomUser(), NormalUser.randomUser()));
    }

    @Test
    public void genericsInsertBatchIgnore() {
        genericsBaseMapper.insertBatchIgnore("", Arrays.asList(NormalUser.randomUser(), NormalUser.randomUser()));
    }

    @After
    public void after() {
        simpleSourceGeneratorMapper.delTestData();
        genericsBaseMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
