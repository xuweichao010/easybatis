package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.select.BaseSelectMapper;
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
public class BaseSelectTest {


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
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(BaseSelectMapper.class));
        
    }

    @Test
    public void selectKey() {
        Method method = chooseMethod(BaseSelectMapper.class, "selectKey");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source <where> `id` = #{id} </where></script>", select);
    }



    @Test
    public void selectTableKey() {
        Method method = chooseMethod(BaseSelectMapper.class, "selectKey");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source <where> `id` = #{id} </where></script>", select);
    }


    @Test
    public void findAll1() {
        Method method = chooseMethod(BaseSelectMapper.class, "findAll1");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT id FROM t_mysql_sql_source</script>", select);
    }

    @Test
    public void findAll() {
        Method method = chooseMethod(BaseSelectMapper.class, "findAll");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        Assert.assertEquals("<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source</script>", select);
    }

    @Test
    public void methodGlobalDynamic() {
        Method method = chooseMethod(BaseSelectMapper.class, "methodGlobalDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name`" +
                " FROM t_mysql_sql_source <where> <if test='name != null'> AND `name` = #{name} </if> </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }


    @Test
    public void methodParamDynamic() {
        Method method = chooseMethod(BaseSelectMapper.class, "methodParamDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source" +
                " <where> <if test='name != null'> AND `name` = #{name} </if> </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void methodGlobalMultiDynamic() {
        Method method = chooseMethod(BaseSelectMapper.class, "methodGlobalMultiDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source" +
                " <where>" +
                " <if test='name != null'> AND `name` = #{name} </if>" +
                " <if test='orgCode != null'> AND `orgCode` = #{orgCode} </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void methodParamMultiDynamic() {
        Method method = chooseMethod(BaseSelectMapper.class, "methodParamMultiDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source" +
                " <where>" +
                " <if test='name != null'> AND `name` = #{name} </if>" +
                " <if test='orgCode != null'> AND `orgCode` = #{orgCode} </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }


    @Test
    public void methodCustom() {
        Method method = chooseMethod(BaseSelectMapper.class, "methodCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source" +
                " <where> <if test='id != null'> AND `id` = #{id} </if>" +
                " <if test='orgCode != null'> AND `orgCode` = #{orgCode} </if>" +
                " <if test='ids != null'> AND `id` IN <foreach item= 'item'  collection='ids' open='(' separator=', ' close=')'>#{item}</foreach> </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);

    }


    @Test
    public void methodMultiCustom() {
        Method method = chooseMethod(BaseSelectMapper.class, "methodMultiCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source" +
                " <where> <if test='one.id != null'> AND `id` = #{one.id} </if>" +
                " <if test='one.orgCode != null'> AND `orgCode` = #{one.orgCode} </if>" +
                " <if test='two.orgName != null'> AND `orgName` = #{two.orgName} </if>" +
                " <if test='two.name != null'> AND `name` = #{two.name} </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void methodMixture() {
        Method method = chooseMethod(BaseSelectMapper.class, "methodMixture");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String targetSql = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_mysql_sql_source" +
                " <where>" +
                " `name` = #{name}" +
                " <if test='orgCode != null'> AND `orgCode` = #{orgCode} </if>" +
                " <if test='two.orgName != null'> AND `orgName` = #{two.orgName} </if>" +
                " <if test='two.name != null'> AND `name` = #{two.name} </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
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
