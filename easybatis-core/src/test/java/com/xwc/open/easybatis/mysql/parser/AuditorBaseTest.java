package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.auditor.AuditorUser;
import com.xwc.open.easybatis.mysql.parser.auditor.AuditorUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class AuditorBaseTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    TableMeta tableMeta;
    AnnotationAssistant annotationAssistant;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        this.annotationAssistant = easybatisConfiguration.getAnnotationAssistant();
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(AuditorUserMapper.class));
    }

    @Test
    public void selectKey() {
        Method method = chooseMethod(AuditorUserMapper.class, "selectKey");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT `id`, `orgCode`, `orgName`, `updateTime`, `updateName`, `updateId` FROM t_user <where> `id` = #{id} </where></script>", sql);
    }

    @Test
    public void insert() {
        Method method = chooseMethod(AuditorUserMapper.class, "insert");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String sqlTarget = "<script>" +
                " INSERT INTO t_user" +
                " (`id`, `orgCode`, `orgName`, `createTime`, `updateTime`, `createName`, `updateName`, `createId`, `updateId`)" +
                " VALUES" +
                " (#{id}, #{orgCode}, #{orgName}, #{createTime}, #{updateTime}, #{createName}, #{updateName}, #{createId}, #{updateId})" +
                " </script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void insertBatch() {
        Method method = chooseMethod(AuditorUserMapper.class, "insertBatch");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String sqlTarget = "<script>" +
                " INSERT INTO t_user" +
                " (`id`, `orgCode`, `orgName`, `createTime`, `updateTime`, `createName`, `updateName`, `createId`, `updateId`)" +
                " VALUES  <foreach item= 'item'  collection='list' separator=', '>" +
                " (#{item.id}, #{item.orgCode}, #{item.orgName}, #{item.createTime}, #{item.updateTime}, #{item.createName}, #{item.updateName}, #{item.createId}, #{item.updateId})" +
                " </foreach>" +
                " </script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void update() {
        Method method = chooseMethod(AuditorUserMapper.class, "update");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(methodMeta);
        String sqlTarget = "<script>" +
                " UPDATE t_user SET `orgCode` = #{orgCode}, `orgName` = #{orgName}, `updateTime` = #{updateTime}, `updateName` = #{updateName}, `updateId` = #{updateId}" +
                " <where> `id` = #{id} </where>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void updateParam() {
        Method method = chooseMethod(AuditorUserMapper.class, "updateParam");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(methodMeta);
        System.out.println(sql);
        String sqlTarget = "<script>" +
                " UPDATE t_user SET `name` = #{name}, `updateTime` = #{updateTime}, `updateName` = #{updateName}, `updateId` = #{updateId}" +
                " <where> `id` = #{id} </where>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
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
