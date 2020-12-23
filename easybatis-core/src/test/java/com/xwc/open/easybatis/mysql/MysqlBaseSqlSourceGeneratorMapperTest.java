package com.xwc.open.easybatis.mysql;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.mybatis.UserMapper;
import com.xwc.open.easybatis.mysql.model.MysqlBaseSqlSourceGeneratorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.stream.Stream;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/18
 * 描述：mysql单元测试
 */
public class MysqlBaseSqlSourceGeneratorMapperTest {


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
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(MysqlBaseSqlSourceGeneratorMapper.class));


    }

    @Test
    public void selectKey() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "selectKey");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source WHERE `id` = #{id}</script>", select);
    }

    @Test
    public void findAll1() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "findAll1");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT id FROM t_mysql_sql_source</script>", select);
    }

    @Test
    public void findAll() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "findAll");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source</script>", select);
    }

    @Test
    public void methodGlobalDynamic() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "methodGlobalDynamic");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`,`orgCode`,`orgName`,`name`" +
                " FROM t_mysql_sql_source WHERE  (#{name} IS NULL OR `name` = #{name})" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void methodParamDynamic() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "methodParamDynamic");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source" +
                " WHERE  (#{name} IS NULL OR `name` = #{name})" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void methodGlobalMultiDynamic() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "methodGlobalMultiDynamic");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source" +
                " WHERE  (#{name} IS NULL OR `name` = #{name})" +
                " AND (#{orgCode} IS NULL OR `orgCode` = #{orgCode})" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void methodParamMultiDynamic() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "methodParamMultiDynamic");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source" +
                " WHERE  (#{name} IS NULL OR `name` = #{name})" +
                " AND (#{orgCode} IS NULL OR `orgCode` = #{orgCode})" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }


    @Test
    public void methodCustom() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "methodCustom");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source" +
                " WHERE <if test='id'>  AND `id` = #{id} </if>" +
                " <if test='orgCode'>  AND `orgCode` = #{orgCode} </if>" +
                "</script>";
        Assert.assertEquals(select, targetSql);

    }


    @Test
    public void methodMultiCustom() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "methodMultiCustom");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source" +
                " WHERE <if test='one.id'>  AND `id` = #{one.id} </if>" +
                " <if test='one.orgCode'>  AND `orgCode` = #{one.orgCode} </if>" +
                " <if test='two.orgName'>  AND `orgName` = #{two.orgName} </if>" +
                " <if test='two.name'>  AND `name` = #{two.name} </if>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void methodMixture() {
        Method method = chooseMethod(MysqlBaseSqlSourceGeneratorMapper.class, "methodMixture");
        MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method,
                tableMeta, AnnotationUtils.findAnnotation(method, SelectSql.class));
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source" +
                " WHERE `name` = #{name}" +
                " AND (#{orgCode} IS NULL OR `orgCode` = #{orgCode})" +
                " <if test='two.orgName'>  AND `orgName` = #{two.orgName} </if>" +
                " <if test='two.name'>  AND `name` = #{two.name} </if>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }


    private Method chooseMethod(Class<?> classType, String methodName) {
        Method[] declaredMethod = classType.getDeclaredMethods();
        for (Method method : declaredMethod) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new RuntimeException(" 找不到方法");
    }
}
