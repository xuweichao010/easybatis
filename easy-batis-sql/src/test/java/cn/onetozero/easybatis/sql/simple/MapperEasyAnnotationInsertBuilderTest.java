package cn.onetozero.easybatis.sql.simple;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.entity.NormalUser;
import cn.onetozero.easybatis.mapper.GenericsBaseMapper;
import cn.onetozero.easybatis.mapper.SimpleSourceGeneratorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/17 10:16
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
