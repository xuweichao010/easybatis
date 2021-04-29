package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherTableUser;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherUser;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2021/1/5
 * 描述：分组测试
 */
public class OtherMapperTest {
    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    OtherTableUserMapper otherTableUserMapper;
    OtherUserMapper otherUserMapper;
    SqlSession sqlSession;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(OtherTableUserMapper.class);
        easybatisConfiguration.addMapper(OtherUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        otherUserMapper = sqlSession.getMapper(OtherUserMapper.class);
        otherTableUserMapper = sqlSession.getMapper(OtherTableUserMapper.class);
    }

    @Test
    public void orderBy() {
        Assert.assertTrue(validateDesc(otherUserMapper.orderByDesc(null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateDesc(otherTableUserMapper.orderByDesc("t_user", null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));

        Assert.assertTrue(validateAsc(otherUserMapper.orderByAsc(null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherTableUserMapper.orderByAsc("t_user", null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));

        Assert.assertTrue(validateDesc(otherUserMapper.orderByMultiDesc(null, null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateDesc(otherTableUserMapper.orderByMultiDesc("t_user", null, null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));

        Assert.assertFalse(validateAsc(otherUserMapper.orderByMethodDynamic(null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertFalse(validateAsc(otherTableUserMapper.orderByMethodDynamic("t_user", null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherUserMapper.orderByMethodDynamic(true).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherTableUserMapper.orderByMethodDynamic("t_user", true).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));

        Assert.assertFalse(validateAsc(otherUserMapper.orderByMethodDynamicMulti(null, null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertFalse(validateAsc(otherTableUserMapper.orderByMethodDynamicMulti("t_user", null, null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherUserMapper.orderByMethodDynamicMulti(true, true).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherTableUserMapper.orderByMethodDynamicMulti("t_user", true, true).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherUserMapper.orderByMethodDynamicMulti(true, null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherTableUserMapper.orderByMethodDynamicMulti("t_user", true, null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateDesc(otherUserMapper.orderByMethodDynamicMulti(null, true).stream().map(OtherUser::getJob).collect(Collectors.toList())));
        Assert.assertTrue(validateDesc(otherTableUserMapper.orderByMethodDynamicMulti("t_user", null, true).stream().map(OtherTableUser::getJob).collect(Collectors.toList())));

        Assert.assertFalse(validateAsc(otherUserMapper.orderByParamDynamic(null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertFalse(validateAsc(otherTableUserMapper.orderByParamDynamic("t_user", null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherUserMapper.orderByParamDynamic(true).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherTableUserMapper.orderByParamDynamic("t_user", true).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));

        Assert.assertFalse(validateAsc(otherUserMapper.orderByParamDynamicMulti(null, null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertFalse(validateAsc(otherTableUserMapper.orderByParamDynamicMulti("t_user", null, null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherUserMapper.orderByParamDynamicMulti(true, true).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherTableUserMapper.orderByParamDynamicMulti("t_user", true, true).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherUserMapper.orderByParamDynamicMulti(true, null).stream().map(OtherUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateAsc(otherTableUserMapper.orderByParamDynamicMulti("t_user", true, null).stream().map(OtherTableUser::getAge).collect(Collectors.toList())));
        Assert.assertTrue(validateDesc(otherUserMapper.orderByParamDynamicMulti(null, true).stream().map(OtherUser::getJob).collect(Collectors.toList())));
        Assert.assertTrue(validateDesc(otherTableUserMapper.orderByParamDynamicMulti("t_user", null, true).stream().map(OtherTableUser::getJob).collect(Collectors.toList())));


    }

    @Test
    public void count() {
        Integer count = otherUserMapper.count();
        Assert.assertEquals(count, Integer.valueOf(31));
    }


    @Test
    public void page() {

    }

    @Test
    public void distinct() {
        List<Integer> integers = otherUserMapper.distinctAge();
        Integer count = otherUserMapper.count();
        if (integers.size() == count) {
            Assert.fail();
        }
    }

    private boolean validateDesc(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            Integer before = list.get(Math.max(i - 1, 0));
            Integer current = list.get(i);
            if (before < current) {
                return false;
            }
        }
        return true;
    }

    private boolean validateAsc(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            Integer before = list.get(Math.max(i - 1, 0));
            Integer current = list.get(i);
            if (before > current) {
                return false;
            }
        }
        return true;
    }


}
