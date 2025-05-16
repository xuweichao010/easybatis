package cn.onetozero.easybatis.sql.fill;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.entity.FillUser;
import cn.onetozero.easybatis.mapper.FillSourceGeneratorMapper;
import cn.onetozero.easybatis.mapper.GenericsBaseMapper;
import cn.onetozero.easybatis.model.NormalUserUpdateObject;
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
import java.util.Arrays;
import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/17 10:16
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
        Environment environment = new SqlSessionFactoryBuilder().build(inputStream).getConfiguration().getEnvironment();
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
        this.easyBatisConfiguration.setEnvironment(environment);
        this.sqlSessionFactory = new DefaultSqlSessionFactory(this.easyBatisConfiguration);
        this.sqlSession = this.sqlSessionFactory.openSession();
        this.easyBatisConfiguration.addMapper(FillSourceGeneratorMapper.class);
        this.easyBatisConfiguration.addMapper(GenericsBaseMapper.class);
        this.fillSourceGeneratorMapper = this.easyBatisConfiguration.getMapper(FillSourceGeneratorMapper.class,
                this.sqlSession);
        this.genericsBaseMapper = this.easyBatisConfiguration.getMapper(GenericsBaseMapper.class, this.sqlSession);
        fillSourceGeneratorMapper.delTestData();
        this.easyBatisConfiguration.addFillAttributeHandler(new AnnotationFillAttribute());
        genericsBaseMapper.delTestData();
    }

    @Test
    public void fillUpdate() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.update(fillUser);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals(fillUser.getUpdateName(), dbUser.getUpdateName());
        Assert.assertEquals(fillUser.getUpdateId(), dbUser.getUpdateId());
    }

    @Test
    public void fillUpdateBatch() {
        List<FillUser> fillUsers = Arrays.asList(createUser(),createUser());
        fillSourceGeneratorMapper.updateBatch(fillUsers);
        fillUsers.forEach(fillUser -> {
            FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
            Assert.assertNotNull(dbUser.getUpdateTime());
            Assert.assertEquals(fillUser.getUpdateName(), dbUser.getUpdateName());
            Assert.assertEquals(fillUser.getUpdateId(), dbUser.getUpdateId());
        });

    }

    @Test
    public void fillUpdateDynamic() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.updateDynamic(fillUser);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals(fillUser.getUpdateName(), dbUser.getUpdateName());
        Assert.assertEquals(fillUser.getUpdateId(), dbUser.getUpdateId());
    }


    @Test
    public void fillUpdateParam() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.updateParam(fillUser.getId(), fillUser.getName());
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());
    }


    @Test
    public void fillUpdateParamDynamic() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.updateParamDynamic(fillUser.getId(), "simpleUpdateParamDynamic", null);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());
    }

    @Test
    public void fillDynamicUpdateParam() {
        FillUser fillUser = createUser();
        fillSourceGeneratorMapper.updateParamDynamic(fillUser.getId(), null, 100);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(fillUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());
    }


    @Test
    public void simpleUpdateObject() {
        FillUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleUpdateObject", null, null);
        fillSourceGeneratorMapper.updateObject(updateObject);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());
    }

    @Test
    public void simpleUpdateObjectIgnore() {
        FillUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleUpdateObjectIgnore", null, null);
        fillSourceGeneratorMapper.updateObjectIgnore(null, updateObject);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());
    }

    @Test
    public void simpleDynamicUpdateObject() {
        FillUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleDynamicUpdateObject", null, null);
        fillSourceGeneratorMapper.dynamicUpdateObject(updateObject);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());
    }

    @Test
    public void simpleDynamicUpdateMixture() {
        FillUser updateUser = createUser();
        NormalUserUpdateObject updateObject = NormalUserUpdateObject.createName(updateUser.getId(),
                "simpleDynamicUpdateMixture", null, null);
        fillSourceGeneratorMapper.dynamicUpdateMixture(updateUser.getName(), updateObject);
        FillUser dbUser = fillSourceGeneratorMapper.findOne(updateUser.getId());
        Assert.assertNotNull(dbUser.getUpdateTime());
        Assert.assertEquals("updateName", dbUser.getUpdateName());
        Assert.assertEquals("-2", dbUser.getUpdateId());

        fillSourceGeneratorMapper.dynamicUpdateMixture(null, updateObject);
        dbUser = fillSourceGeneratorMapper.findOne(updateUser.getId());
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
