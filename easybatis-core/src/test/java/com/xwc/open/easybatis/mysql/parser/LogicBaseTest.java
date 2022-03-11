package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.logic.LogicBaseMapper;
import com.xwc.open.easybatis.mysql.parser.logic.LogicTableBaseMapper;
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
public class LogicBaseTest {


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
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(LogicBaseMapper.class));
    }

    @Test
    public void findAll() {
        Method method = chooseMethod(LogicBaseMapper.class, "findAll");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName` FROM t_user" +
                " <where> `valid` = #{valid} </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findTableAll() {
        Method method = chooseMethod(LogicTableBaseMapper.class, "findAll");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName` FROM t_user" +
                " <where> `valid` = #{valid} </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void selectKey() {
        Method method = chooseMethod(LogicBaseMapper.class, "selectKey");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName` FROM t_user <where> `id` = #{id} AND `valid` = #{valid} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Method method = chooseMethod(LogicBaseMapper.class, "update");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().update(methodMeta);
        String expected = "<script> UPDATE t_user <set> `orgCode` = #{orgCode}, `orgName` = #{orgName}, </set> <where> `id` = #{id} AND `valid` = #{valid} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateActivate() {
        Method method = chooseMethod(LogicBaseMapper.class, "updateActivate");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().update(methodMeta);
        String expected = "<script>"
                + " UPDATE t_user"
                + " <set>"
                + " <if test='orgCode != null'> `orgCode` = #{orgCode},</if>"
                + " <if test='orgName != null'> `orgName` = #{orgName},</if>"
                + " </set>"
                + " <where> `id` = #{id} AND `valid` = #{valid} </where>"
                + "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insert() {
        Method method = chooseMethod(LogicBaseMapper.class, "insert");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script> INSERT INTO t_user (`id`, `orgCode`, `orgName`, `valid`) VALUES (#{id}, #{orgCode}, #{orgName}, #{valid}) </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void insertBatch() {
        Method method = chooseMethod(LogicBaseMapper.class, "insertBatch");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().insert(methodMeta);
        String expected = "<script>" +
                " INSERT INTO t_user" +
                " (`id`, `orgCode`, `orgName`, `valid`)" +
                " VALUES" +
                " <foreach item= 'item'  collection='collection' separator=', '>" +
                " (#{item.id}, #{item.orgCode}, #{item.orgName}, #{item.valid})" +
                " </foreach>" +
                " </script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void delete() {
        Method method = chooseMethod(LogicBaseMapper.class, "delete");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().delete(methodMeta);
        String expected = "<script> UPDATE t_user <set> `valid` = #{valid0}, </set> <where> `id` = #{id} AND `valid` = #{valid} </where></script>";
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
