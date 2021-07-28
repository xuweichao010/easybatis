package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.other.OtherMapper;
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
public class OtherTest {

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
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(OtherMapper.class));
    }


    @Test
    public void countAll() {
        Method method = chooseMethod(OtherMapper.class, "countAll");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script> SELECT COUNT(*) FROM t_condition</script>";
        Assert.assertEquals(sql, sqlTarget);

    }

    @Test
    public void countCondition() {
        Method method = chooseMethod(OtherMapper.class, "countCondition");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script> SELECT COUNT(*) FROM t_condition <where> `name` = #{name} AND `age` = #{age} </where></script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void distinctAll() {
        Method method = chooseMethod(OtherMapper.class, "distinctAll");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script> SELECT DISTINCT(`id`, `orgCode`, `orgName`, `name`) FROM t_condition</script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void distinctCondition() {
        Method method = chooseMethod(OtherMapper.class, "distinctCondition");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script> SELECT DISTINCT(`id`, `orgCode`, `orgName`, `name`) FROM t_condition <where> `name` = #{name} AND `age` = #{age} </where></script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void distinctCustomCondition() {
        Method method = chooseMethod(OtherMapper.class, "distinctCustomCondition");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script> SELECT DISTINCT(id) FROM t_condition <where> `name` = #{name} AND `age` = #{age} </where></script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void distinctCountCondition() {
        Method method = chooseMethod(OtherMapper.class, "distinctCountCondition");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script> SELECT COUNT(DISTINCT(id)) FROM t_condition <where> `name` = #{name} AND `age` = #{age} </where></script>";
        Assert.assertEquals(sql, sqlTarget);
    }


    @Test
    public void orderBy() {
        Method method = chooseMethod(OtherMapper.class, "orderBy");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT id FROM t_condition" +
                " <where> `name` = #{name} </where>" +
                " <trim prefix='ORDER BY' suffixOverrides=','>" +
                " `name` ASC," +
                " </trim>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void orderByMulti() {
        Method method = chooseMethod(OtherMapper.class, "orderByMulti");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT id FROM t_condition" +
                " <where> `name` = #{name} </where>" +
                " <trim prefix='ORDER BY' suffixOverrides=','>" +
                " `name` ASC, `job` DESC," +
                " </trim>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void orderByMethodDynamic() {
        Method method = chooseMethod(OtherMapper.class, "orderByMethodDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name`" +
                " FROM t_condition" +
                " <where>" +
                " <if test='name != null'> AND `name` = #{name} </if>" +
                " </where>" +
                " <trim prefix='ORDER BY' suffixOverrides=','>" +
                " <if test='age != null'> `age` ASC,</if>" +
                " </trim>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void orderByMethodDynamicMulti() {
        Method method = chooseMethod(OtherMapper.class, "orderByMethodDynamicMulti");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name`" +
                " FROM t_condition" +
                " <where>" +
                " <if test='name != null'> AND `name` = #{name} </if>" +
                " </where>" +
                " <trim prefix='ORDER BY' suffixOverrides=','>" +
                " <if test='age != null'> `age` ASC,</if>" +
                " <if test='job != null'> `job` DESC,</if>" +
                " </trim>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void orderByParamDynamic() {
        Method method = chooseMethod(OtherMapper.class, "orderByParamDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name`" +
                " FROM t_condition" +
                " <where>" +
                " `name` = #{name}" +
                " </where>" +
                " <trim prefix='ORDER BY' suffixOverrides=','>" +
                " <if test='age != null'> `age` ASC,</if>" +
                " </trim>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
    }

    @Test
    public void orderByParamDynamicMulti() {
        Method method = chooseMethod(OtherMapper.class, "orderByParamDynamicMulti");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where>" +
                " <if test='name != null'> AND `name` = #{name} </if>" +
                " </where>" +
                " <trim prefix='ORDER BY' suffixOverrides=','>" +
                " <if test='age != null'> `age` ASC,</if>" +
                " <if test='job != null'> `job` DESC,</if>" +
                " </trim>" +
                "</script>";
        Assert.assertEquals(sql, sqlTarget);
    }



    //@Test
    public void page() {
        Method method = chooseMethod(OtherMapper.class, "limit");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String sqlTarget = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " LIMIT #{start}, #{offset}" +
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
