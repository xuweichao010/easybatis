package com.xwc.open.easybatis.mysql.mybatis;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisTableUser;
import com.xwc.open.easybatis.mysql.mybatis.base.MybatisUser;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicTableUser;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicUser;
import com.xwc.open.easybatis.mysql.mybatis.logic.LogicUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherTableUser;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherTableUserMapper;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherUser;
import com.xwc.open.easybatis.mysql.mybatis.other.OtherUserMapper;
import com.xwc.open.easybatis.mysql.parser.logic.LogicBaseMapper;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2021/1/5
 * 描述：分组测试
 */
public class LogicMapperTest {
    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    LogicTableUserMapper logicTableUserMapper;
    LogicUserMapper logicUserMapper;
    SqlSession sqlSession;
    String logicTableUserId;
    String logicUserId;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(LogicUserMapper.class);
        easybatisConfiguration.addMapper(LogicTableUserMapper.class);
        sqlSession = sqlSessionFactory.openSession(true);
        logicUserMapper = sqlSession.getMapper(LogicUserMapper.class);
        logicTableUserMapper = sqlSession.getMapper(LogicTableUserMapper.class);
        LogicTableUser logicTableUser = genderLogicTableUser();
        this.logicTableUserId = uuid();
        logicTableUser.setId(logicTableUserId);
        logicTableUserMapper.insert("t_user", logicTableUser);
        LogicUser logicUser = genderLogicUser();
        this.logicUserId = uuid();
        logicUser.setId(logicUserId);
        logicUserMapper.insert(logicUser);
    }

    @After
    public void after() {
        logicUserMapper.deleteByValid(LogicUser.LOGIC_VALID);
        logicUserMapper.deleteByValid(LogicUser.LOGIC_INVALID);
    }

    @Test
    public void insert() {
        LogicTableUser logicTableUser = genderLogicTableUser();
        LogicUser logicUser = genderLogicUser();
        logicUserMapper.insert(logicUser);
        Assert.assertEquals((int) logicUser.getValid(), LogicUser.LOGIC_VALID);
        logicTableUserMapper.insert("t_user", logicTableUser);
        Assert.assertEquals((int) logicTableUser.getValid(), LogicUser.LOGIC_VALID);
    }

    @Test
    public void insertBatch() {
        List<LogicUser> list = Arrays.asList(genderLogicUser(), genderLogicUser(), genderLogicUser());
        logicUserMapper.insertBatch(list);
        for (LogicUser user : list) {
            Assert.assertEquals((int) user.getValid(), LogicUser.LOGIC_VALID);
        }
        List<LogicTableUser> tableList = Arrays.asList(genderLogicTableUser(), genderLogicTableUser(), genderLogicTableUser());
        logicTableUserMapper.insertBatch("t_user", tableList);
        for (LogicTableUser user : tableList) {
            Assert.assertEquals((int) user.getValid(), LogicUser.LOGIC_VALID);
        }
    }


    @Test
    public void selectKey() {
        logicUserMapper.selectKey("450ba958f9e247509188473ff27cd726");
        LogicUser logicUser = logicUserMapper.selectKey1("450ba958f9e247509188473ff27cd726", 100);
    }



//    @SelectSql
//    E selectKey(K id);
//
//    @UpdateSql
//    Integer update(E entity);
//
//    @UpdateSql(dynamic = true)
//    Integer updateActivate(E entity);
//
//    @InsertSql
//    Integer insert(E entity);
//
//    @InsertSql
//    Integer insertBatch(Collection<E> list);
//
//    @DeleteSql
//    Integer delete(K id);

    private LogicUser genderLogicUser() {
        Random random = new Random();
        LogicUser tar = new LogicUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        return tar;
    }

    public String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private LogicTableUser genderLogicTableUser() {
        Random random = new Random();
        LogicTableUser tar = new LogicTableUser();
        tar.setAge(random.nextInt(100));
        tar.setId(uuid());
        tar.setJob(random.nextInt(5));
        tar.setName(uuid().substring(0, 6));
        tar.setOrgCode("200");
        tar.setOrgName("总公司");
        return tar;
    }


}
