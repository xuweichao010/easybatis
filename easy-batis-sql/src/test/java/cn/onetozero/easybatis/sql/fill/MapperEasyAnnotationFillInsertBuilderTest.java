package cn.onetozero.easybatis.sql.fill;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.entity.FillUser;
import cn.onetozero.easybatis.mapper.FillSourceGeneratorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 10:16
 */
public class MapperEasyAnnotationFillInsertBuilderTest {


    SqlSessionFactory sqlSessionFactory;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSession sqlSession;
    FillSourceGeneratorMapper fillSourceGeneratorMapper;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        Environment environment = new SqlSessionFactoryBuilder().build(inputStream).getConfiguration().getEnvironment();
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
        this.easyBatisConfiguration.setEnvironment(environment);
        this.sqlSessionFactory = new DefaultSqlSessionFactory(this.easyBatisConfiguration);
    ;
        this.sqlSession = sqlSessionFactory.openSession();
        this.sqlSession.getConfiguration().addMapper(FillSourceGeneratorMapper.class);
        this.fillSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(FillSourceGeneratorMapper.class,
                sqlSession);
        fillSourceGeneratorMapper.delTestData();
        this.easyBatisConfiguration.addFillAttributeHandler(new AnnotationFillAttribute());
    }

    @Test
    public void fillInsert() {
        FillUser fillUser = FillUser.randomUser();
        fillSourceGeneratorMapper.insert(fillUser);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertTrue(fillUser.getCreateId() != null && fillUser.getCreateId().equals(dbUser.getCreateId()));
        Assert.assertTrue(fillUser.getCreateName() != null && fillUser.getCreateName().equals(dbUser.getCreateName()));
        Assert.assertTrue(fillUser.getCreateTime() != null && dbUser.getCreateTime() != null);
        Assert.assertTrue(fillUser.getUpdateTime() != null && dbUser.getUpdateTime() != null);

    }

    @Test
    public void fillInsertIgnore() {
        FillUser fillUser = FillUser.randomUser();
        fillSourceGeneratorMapper.insertIgnore(null, fillUser);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertTrue(fillUser.getCreateId() != null && fillUser.getCreateId().equals(dbUser.getCreateId()));
        Assert.assertTrue(fillUser.getCreateName() != null && fillUser.getCreateName().equals(dbUser.getCreateName()));
        Assert.assertTrue(fillUser.getCreateTime() != null && dbUser.getCreateTime() != null);
        Assert.assertTrue(fillUser.getUpdateTime() != null && dbUser.getUpdateTime() != null);
    }

    @Test
    public void simpleInsertBatch() {
        FillUser fillUser = FillUser.randomUser();
        fillSourceGeneratorMapper.insertBatch(Collections.singletonList(fillUser));
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertTrue(fillUser.getCreateId() != null && fillUser.getCreateId().equals(dbUser.getCreateId()));
        Assert.assertTrue(fillUser.getCreateName() != null && fillUser.getCreateName().equals(dbUser.getCreateName()));
        Assert.assertTrue(fillUser.getCreateTime() != null && dbUser.getCreateTime() != null);
        Assert.assertTrue(fillUser.getUpdateTime() != null && dbUser.getUpdateTime() != null);
    }

    @Test
    public void simpleInsertBatchIgnore() {
        FillUser fillUser = FillUser.randomUser();
        fillSourceGeneratorMapper.insertBatchIgnore(null, Collections.singletonList(fillUser));
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertTrue(fillUser.getCreateId() != null && fillUser.getCreateId().equals(dbUser.getCreateId()));
        Assert.assertTrue(fillUser.getCreateName() != null && fillUser.getCreateName().equals(dbUser.getCreateName()));
        Assert.assertTrue(fillUser.getCreateTime() != null && dbUser.getCreateTime() != null);
        Assert.assertTrue(fillUser.getUpdateTime() != null && dbUser.getUpdateTime() != null);
    }


    @After
    public void after() {
        fillSourceGeneratorMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
