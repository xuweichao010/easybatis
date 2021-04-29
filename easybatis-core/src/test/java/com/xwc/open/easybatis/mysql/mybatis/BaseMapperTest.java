package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisTableUser;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisUser;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：简单的mysql测试
 */
public class BaseMapperTest {


    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    MybatisTableUserMapper mybatisTableUserMapper;
    MybatisUserMapper mybatisUserMapper;
    SqlSession sqlSession;
    int TEST_TAG = 100;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(MybatisTableUserMapper.class);
        easybatisConfiguration.addMapper(MybatisUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        mybatisTableUserMapper = sqlSession.getMapper(MybatisTableUserMapper.class);
        mybatisUserMapper = sqlSession.getMapper(MybatisUserMapper.class);
    }

    @After
    public void  after(){
        mybatisTableUserMapper.clearTest("t_user", TEST_TAG);
        mybatisUserMapper.clearTest(TEST_TAG);
    }


    @Test
    public void selectKey() {
        MybatisUser mybatisUser = mybatisUserMapper.selectKey("37bd0225cc94400db744aac8dee8a001");
        Assert.assertNotNull(mybatisUser);
        MybatisTableUser mybatisTableUser = mybatisTableUserMapper.selectKey("t_user", "37bd0225cc94400db744aac8dee8a001");
        Assert.assertNotNull(mybatisTableUser);
    }


    @Test
    public void insert() {
        MybatisUser mybatisUser = genderMybatisUser();
        MybatisTableUser mybatisTableUser = genderMybatisTableUser();
        try {
            mybatisUserMapper.insert(mybatisUser);
            mybatisTableUserMapper.insert("t_user", mybatisTableUser);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        mybatisUser = mybatisUserMapper.selectKey(mybatisUser.getId());
        System.out.println(mybatisUser);
        Assert.assertNotNull(mybatisUser);
        mybatisTableUser = mybatisTableUserMapper.selectKey("t_user", mybatisTableUser.getId());
        System.out.println(mybatisTableUser);
        Assert.assertNotNull(mybatisTableUser);
    }

    @Test
    public void insertBatch() {
        List<MybatisUser> mybatisUserList = Arrays.asList(genderMybatisUser(), genderMybatisUser(), genderMybatisUser());
        List<MybatisTableUser> mybatisTableUser = Arrays.asList(genderMybatisTableUser(), genderMybatisTableUser(), genderMybatisTableUser());
        try {
            mybatisUserMapper.insertBatch(mybatisUserList);
            mybatisTableUserMapper.insertBatch("t_user", mybatisTableUser);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }


    @Test
    public void update() {
        MybatisUser mybatisUser = genderMybatisUser();
        MybatisTableUser mybatisTableUser = genderMybatisTableUser();
        try {
            mybatisUserMapper.insert(mybatisUser);
            mybatisUser.setName("testName");
            mybatisUserMapper.update(mybatisUser);
            mybatisTableUserMapper.insert("t_user", mybatisTableUser);
            mybatisTableUser.setName("testName");
            mybatisTableUserMapper.update("t_user", mybatisTableUser);

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        mybatisUser = mybatisUserMapper.selectKey(mybatisUser.getId());
        Assert.assertEquals("testName", mybatisUser.getName());
        mybatisTableUser = mybatisTableUserMapper.selectKey("t_user", mybatisTableUser.getId());
        Assert.assertEquals("testName", mybatisTableUser.getName());
        Assert.assertNotNull(mybatisTableUser);
    }


    @Test
    public void delete() {
        MybatisUser mybatisUser = genderMybatisUser();
        MybatisTableUser mybatisTableUser = genderMybatisTableUser();
        try {
            mybatisUserMapper.insert(mybatisUser);
            mybatisTableUserMapper.insert("t_user", mybatisTableUser);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
        Integer delete = mybatisUserMapper.delete(mybatisUser.getId());
        Assert.assertEquals(delete, Integer.valueOf(1));
        delete = mybatisTableUserMapper.delete("t_user", mybatisTableUser.getId());
        Assert.assertEquals(delete, Integer.valueOf(1));
    }

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private MybatisUser genderMybatisUser() {
        Random random = new Random();
        MybatisUser tar = new MybatisUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        tar.setValid(TEST_TAG);
        return tar;
    }

    private MybatisTableUser genderMybatisTableUser() {
        Random random = new Random();
        MybatisTableUser tar = new MybatisTableUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        tar.setValid(TEST_TAG);
        return tar;
    }


    private Method chooseMethod(Class<?> classType, String methodName) {
        Method[] declaredMethod = classType.getMethods();
        for (Method method : declaredMethod) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new RuntimeException(" 找不到方法");
    }
}
