package com.xwc.open.easybatis.mysql;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.mysql.condition.ConditionMapper;
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
 * 时间：2020/12/31
 * 描述：比较查询单元测试
 */
public class CompareConditionTest {

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
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(ConditionMapper.class));
    }

    @Test
    public void defaultEqual() {
        Method method = chooseMethod(ConditionMapper.class, "defaultEqual");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " WHERE `name` = #{name}" +
                "</script>";
        Assert.assertEquals(sqlTarget, sql);
    }

    @Test
    public void defaultEqualDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "defaultEqualDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " WHERE  (#{name} IS NULL OR `name` = #{name})" +
                "</script>";
        Assert.assertEquals(sqlTarget, sql);
    }

    @Test
    public void equalAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "equalAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " WHERE `name` = #{name}" +
                "</script>";
        Assert.assertEquals(sqlTarget, sql);
    }

    @Test
    public void equalAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "equalAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " WHERE  (#{name} IS NULL OR `name` = #{name})" +
                "</script>";
        Assert.assertEquals(sqlTarget, sql);
    }

    @Test
    public void equalAnnotationCustomColumn() {
        Method method = chooseMethod(ConditionMapper.class, "equalAnnotationCustomColumn");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " WHERE `custom_name` = #{name}" +
                "</script>";
        Assert.assertEquals(sqlTarget, sql);
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
