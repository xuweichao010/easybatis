package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.insert.BaseInsertMapper;
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

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/18
 * 描述：mysql单元测试
 */
public class BaseInsertTest {


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
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(BaseInsertMapper.class));
    }

    @Test
    public void insert() {
        Method method = chooseMethod(BaseInsertMapper.class, "insert");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_mysql_sql_source" +
                " (`id`, `orgCode`, `orgName`, `name`)" +
                " VALUES" +
                " (#{id}, #{orgCode}, #{orgName}, #{name})" +
                " </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertEntity() {
        Method method = chooseMethod(BaseInsertMapper.class, "insertEntity");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_mysql_sql_source" +
                " (`id`, `orgCode`, `orgName`, `name`)" +
                " VALUES" +
                " (#{id}, #{orgCode}, #{orgName}, #{name})" +
                " </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertMulti() {
        Method method = chooseMethod(BaseInsertMapper.class, "insertMulti");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_mysql_sql_source (`id`, `orgCode`, `orgName`, `name`)" +
                " VALUES" +
                " (#{entity.id}, #{entity.orgCode}, #{entity.orgName}, #{entity.name})" +
                " </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertBatch() {
        Method method = chooseMethod(BaseInsertMapper.class, "insertBatch");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_mysql_sql_source" +
                " (`id`, `orgCode`, `orgName`, `name`)" +
                " VALUES" +
                " <foreach item= 'item'  collection='collection' separator=', '>" +
                " (#{item.id}, #{item.orgCode}, #{item.orgName}, #{item.name})" +
                " </foreach>" +
                " </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertBatchEntity() {
        Method method = chooseMethod(BaseInsertMapper.class, "insertBatchEntity");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_mysql_sql_source" +
                " (`id`, `orgCode`, `orgName`, `name`)" +
                " VALUES" +
                " <foreach item= 'item'  collection='collection' separator=', '>" +
                " (#{item.id}, #{item.orgCode}, #{item.orgName}, #{item.name})" +
                " </foreach>" +
                " </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertBatchMixture() {
        Method method = chooseMethod(BaseInsertMapper.class, "insertBatchMixture");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_mysql_sql_source" +
                " (`id`, `orgCode`, `orgName`, `name`)" +
                " VALUES" +
                " <foreach item= 'item'  collection='listParam' separator=', '>" +
                " (#{item.id}, #{item.orgCode}, #{item.orgName}, #{item.name})" +
                " </foreach>" +
                " </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertBatchEntityMixture() {
        Method method = chooseMethod(BaseInsertMapper.class, "insertBatchEntityMixture");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_mysql_sql_source" +
                " (`id`, `orgCode`, `orgName`, `name`)" +
                " VALUES" +
                " <foreach item= 'item'  collection='listParam' separator=', '>" +
                " (#{item.id}, #{item.orgCode}, #{item.orgName}, #{item.name})" +
                " </foreach>" +
                " </script>";
        Assert.assertEquals(expected, actual);
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
