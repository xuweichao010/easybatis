package cn.onetozero.easybatis.sql.simple;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.model.NormalUseQueryDto;
import cn.onetozero.easybatis.model.NormalUserPageQueryDto;
import cn.onetozero.easybatis.model.PageQueryDto;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 类描述：单元测试
 * @author  徐卫超 (cc)
 * @since 2023/1/17 13:30
 */
public class MapperEasyAnnotationSelectBuilderTest {

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
    public void simpleQueryObject() {
        List<NormalUser> normalUsers = simpleSourceGeneratorMapper.queryObject(NormalUserPageQueryDto.createAllConditionQueryObject());
        NormalUser user = normalUsers.stream().filter(normalUser -> normalUser.getName().equals("曹操")).findAny().orElse(null);
        Assert.assertNotNull(user);
        normalUsers = simpleSourceGeneratorMapper.queryObject(NormalUserPageQueryDto.createNoDynamicConditionQueryObject());
        user = normalUsers.stream().filter(normalUser -> normalUser.getName().equals("曹操")).findAny().orElse(null);
        Assert.assertNotNull(user);
        normalUsers = simpleSourceGeneratorMapper.queryObject(NormalUserPageQueryDto.createDynamicErrorQueryObject());
        if (!normalUsers.isEmpty()) {
            Assert.fail();
        }
    }

    @Test
    public void simpleQueryObjectIgnore() {
        List<NormalUser> normalUsers =
                simpleSourceGeneratorMapper.queryObjectIgnore(null,
                        NormalUserPageQueryDto.createAllConditionQueryObject());
        NormalUser user = normalUsers.stream().filter(normalUser -> normalUser.getName().equals("曹操")).findAny().orElse(null);
        Assert.assertNotNull(user);
    }


    @Test
    public void simpleDynamicQueryObject() {
        List<NormalUser> normalUsers =
                simpleSourceGeneratorMapper.dynamicQueryObject(NormalUserPageQueryDto.createAllDynamicConditionQueryObject());
        NormalUser user = normalUsers.stream().filter(normalUser -> normalUser.getName().equals("曹操")).findAny().orElse(null);
        Assert.assertNotNull(user);
    }

    @Test
    public void simpleDynamicQueryObjectIgnore() {
        List<NormalUser> normalUsers =
                simpleSourceGeneratorMapper.dynamicQueryObjectIgnore(null,
                        NormalUserPageQueryDto.createAllDynamicConditionQueryObject());
        NormalUser user = normalUsers.stream().filter(normalUser -> normalUser.getName().equals("曹操")).findAny().orElse(null);
        Assert.assertNotNull(user);
    }

    @Test
    public void simpleQueryObjectCount() {
        Long actual = simpleSourceGeneratorMapper.queryObjectCount(NormalUserPageQueryDto.createAllConditionQueryObject());
        Assert.assertEquals(Long.valueOf(1), actual);
    }

    @Test
    public void simpleQueryObjectDistinct() {
        List<Integer> ages = simpleSourceGeneratorMapper.queryObjectDistinct(NormalUserPageQueryDto.createAllConditionQueryObject());
        Assert.assertEquals(1, ages.size());
    }

    @Test
    public void simpleQueryObjectCountDistinct() {
        Long actual =
                simpleSourceGeneratorMapper.queryObjectCountDistinct(NormalUserPageQueryDto.createAllConditionQueryObject());
        Assert.assertEquals(Long.valueOf(1), actual);
    }

    @Test
    public void simpleQueryMultiObject() {
        List<NormalUser> users =
                simpleSourceGeneratorMapper.queryMultiObject(NormalUseQueryDto.createAllConditionQueryObject(),
                PageQueryDto.create(1, 10));
        Assert.assertEquals(1, users.size());
    }


}
