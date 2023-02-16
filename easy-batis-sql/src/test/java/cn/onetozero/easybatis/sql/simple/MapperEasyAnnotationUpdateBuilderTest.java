package cn.onetozero.easybatis.sql.simple;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.model.NormalUserUpdateObject;
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

    private NormalUser createUser() {
        NormalUser updateUser = NormalUser.randomUser();
        simpleSourceGeneratorMapper.insert(updateUser);
        return updateUser;
    }

    @Test
    public void simpleUpdate() {
        NormalUser updateUser = createUser();
        updateUser.setName("simpleUpdate");
        simpleSourceGeneratorMapper.update(updateUser);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(updateUser.getName(), dbUser.getName());
    }

    @Test
    public void simpleUpdateDynamic() {
        NormalUser updateUser = createUser();
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
        NormalUser updateUser = createUser();
        simpleSourceGeneratorMapper.updateParam(updateUser.getId(), "simpleUpdateParam");
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals("simpleUpdateParam", dbUser.getName());
    }


    @Test
    public void simpleUpdateParamDynamic() {
        NormalUser updateUser = createUser();
        simpleSourceGeneratorMapper.updateParamDynamic(updateUser.getId(), "simpleUpdateParamDynamic", null);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals("simpleUpdateParamDynamic", dbUser.getName());
        Assert.assertEquals(dbUser.getAge(), updateUser.getAge());
    }

    @Test
    public void simpleDynamicUpdateParam() {
        NormalUser updateUser = createUser();
        simpleSourceGeneratorMapper.updateParamDynamic(updateUser.getId(), null, 100);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(100, (int) dbUser.getAge());
        Assert.assertEquals(dbUser.getName(), updateUser.getName());
    }


    @Test
    public void simpleUpdateObject() {
        NormalUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleUpdateObject", null, null);
        simpleSourceGeneratorMapper.updateObject(updateObject);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertNull(dbUser.getOrgName());
        Assert.assertNull(dbUser.getOrgCode());
        Assert.assertEquals(updateObject.getName(), dbUser.getName());
    }

    @Test
    public void simpleUpdateObjectIgnore() {
        NormalUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleUpdateObjectIgnore", null, null);
        simpleSourceGeneratorMapper.updateObjectIgnore(null, updateObject);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertNull(dbUser.getOrgName());
        Assert.assertNull(dbUser.getOrgCode());
        Assert.assertEquals(updateObject.getName(), dbUser.getName());
    }

    @Test
    public void simpleDynamicUpdateObject() {
        NormalUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleDynamicUpdateObject", null, null);
        simpleSourceGeneratorMapper.dynamicUpdateObject(updateObject);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(updateUser.getOrgName(), dbUser.getOrgName());
        Assert.assertEquals(updateUser.getOrgCode(), dbUser.getOrgCode());
        Assert.assertEquals(updateObject.getName(), dbUser.getName());
    }

    @Test
    public void simpleDynamicUpdateMixture() {
        NormalUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleDynamicUpdateMixture", null, null);
        simpleSourceGeneratorMapper.dynamicUpdateMixture(updateUser.getName(), updateObject);
        NormalUser dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(updateUser.getOrgName(), dbUser.getOrgName());
        Assert.assertEquals(updateUser.getOrgCode(), dbUser.getOrgCode());
        Assert.assertEquals(updateObject.getName(), dbUser.getName());

        simpleSourceGeneratorMapper.dynamicUpdateMixture(null, updateObject);
        dbUser = simpleSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertEquals(updateUser.getOrgName(), dbUser.getOrgName());
        Assert.assertEquals(updateUser.getOrgCode(), dbUser.getOrgCode());
        Assert.assertEquals(updateObject.getName(), dbUser.getName());
    }


    @After
    public void after() {
        simpleSourceGeneratorMapper.delTestData();
        genericsBaseMapper.delTestData();
        sqlSession.commit();
        sqlSession.close();
    }


}
