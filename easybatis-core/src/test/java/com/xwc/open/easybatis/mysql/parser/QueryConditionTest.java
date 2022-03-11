package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.condition.ConditionMapper;
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
public class QueryConditionTest {

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
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where> `name` = #{name} </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void defaultEqualDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "defaultEqualDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where> <if test='name != null'> AND `name` = #{name} </if> </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "equalAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where> `name` = #{name} </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "equalAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where> <if test='name != null'> AND `name` = #{name} </if> </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void equalAnnotationCustomColumn() {
        Method method = chooseMethod(ConditionMapper.class, "equalAnnotationCustomColumn");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where> `custom_name` = #{name} </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notEqualAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "notEqualAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where> `name` != #{name} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notEqualAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "notEqualAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` != #{name} </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notEqualAnnotationCustom() {
        Method method = chooseMethod(ConditionMapper.class, "notEqualAnnotationCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `custom_name` != #{name} </where></script>";
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void greaterThanAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "greaterThanAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `age` <![CDATA[>]]> #{age} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void greaterThanAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "greaterThanAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='age != null'> AND `age` <![CDATA[>]]> #{age} </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void greaterThanAnnotationCustom() {
        Method method = chooseMethod(ConditionMapper.class, "greaterThanAnnotationCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `custom_age` <![CDATA[>]]> #{age} </where></script>";
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void lessThanAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "lessThanAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `age` <![CDATA[<]]> #{age} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lessThanAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "lessThanAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='age != null'> AND `age` <![CDATA[<]]> #{age} </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lessThanAnnotationCustom() {
        Method method = chooseMethod(ConditionMapper.class, "lessThanAnnotationCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `age` <![CDATA[<]]> #{customAge} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void greaterThanEqualAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "greaterThanEqualAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `age` <![CDATA[>=]]> #{age} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void greaterThanEqualAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "greaterThanEqualAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='age != null'> AND `age` <![CDATA[>=]]> #{age} </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void greaterThanEqualAnnotationCustom() {
        Method method = chooseMethod(ConditionMapper.class, "greaterThanEqualAnnotationCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `age` <![CDATA[>=]]> #{customAge} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lessThanEqualAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "lessThanEqualAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `age` <![CDATA[<=]]> #{age} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lessThanEqualAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "lessThanEqualAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='age != null'> AND `age` <![CDATA[<=]]> #{age} </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void lessThanEqualAnnotationCustom() {
        Method method = chooseMethod(ConditionMapper.class, "lessThanEqualAnnotationCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `age` <![CDATA[<=]]> #{customAge} </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isNullAnnotation() {
        Method method = chooseMethod(ConditionMapper.class, "isNullAnnotation");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `name` IS NULL </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isNullAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "isNullAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` IS NULL </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isNullAnnotationCustom() {
        Method method = chooseMethod(ConditionMapper.class, "isNullAnnotationCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='customName != null'> AND `name` IS NULL </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isNotNullAnnotationDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "isNotNullAnnotationDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` IS NOT NULL </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void isNotNullAnnotationCustom() {
        Method method = chooseMethod(ConditionMapper.class, "isNotNullAnnotationCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='customName != null'> AND `name` IS NOT NULL </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void like() {
        Method method = chooseMethod(ConditionMapper.class, "like");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `name` LIKE CONCAT('%',#{name},'%') </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void likeDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "likeDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` LIKE CONCAT('%',#{name},'%') </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void likeCustom() {
        Method method = chooseMethod(ConditionMapper.class, "likeCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where>" +
                " <if test='customName != null'> AND `name` LIKE CONCAT('%',#{customName},'%') </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void leftLike() {
        Method method = chooseMethod(ConditionMapper.class, "leftLike");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `name` LIKE CONCAT('%',#{name}) </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void leftLikeDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "leftLikeDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` LIKE CONCAT('%',#{name}) </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void leftLikeCustom() {
        Method method = chooseMethod(ConditionMapper.class, "leftLikeCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='customName != null'> AND `name` LIKE CONCAT('%',#{customName}) </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void rightLike() {
        Method method = chooseMethod(ConditionMapper.class, "rightLike");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `name` LIKE CONCAT(#{name},'%') </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void rightLikeDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "rightLikeDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` LIKE CONCAT(#{name},'%') </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void rightLikeCustom() {
        Method method = chooseMethod(ConditionMapper.class, "rightLikeCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='customName != null'> AND `name` LIKE CONCAT(#{customName},'%') </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notLike() {
        Method method = chooseMethod(ConditionMapper.class, "notLike");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `name` NOT LIKE CONCAT('%',#{name},'%') </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notLikeDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "notLikeDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` NOT LIKE CONCAT('%',#{name},'%') </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notLikeCustom() {
        Method method = chooseMethod(ConditionMapper.class, "notLikeCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='customName != null'> AND `name` NOT LIKE CONCAT('%',#{customName},'%') </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notLeftLike() {
        Method method = chooseMethod(ConditionMapper.class, "notLeftLike");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `name` NOT LIKE CONCAT('%',#{name}) </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notLeftLikeDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "notLeftLikeDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` NOT LIKE CONCAT('%',#{name}) </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notLeftLikeCustom() {
        Method method = chooseMethod(ConditionMapper.class, "notLeftLikeCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='customName != null'> AND `name` NOT LIKE CONCAT('%',#{customName}) </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notRightLike() {
        Method method = chooseMethod(ConditionMapper.class, "notRightLike");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> `name` NOT LIKE CONCAT(#{name},'%') </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notRightLikeDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "notRightLikeDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='name != null'> AND `name` NOT LIKE CONCAT(#{name},'%') </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notRightLikeCustom() {
        Method method = chooseMethod(ConditionMapper.class, "notRightLikeCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition <where> <if test='customName != null'> AND `name` NOT LIKE CONCAT(#{customName},'%') </if> </where></script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void in() {
        Method method = chooseMethod(ConditionMapper.class, "in");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name`" +
                " FROM t_condition" +
                " <where>" +
                " `name` IN <foreach item= 'item'  collection='collection' open='(' separator=', ' close=')'>#{item}</foreach>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void inDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "inDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where> " +
                "<if test='collection != null'> AND `name` IN <foreach item= 'item'  collection='collection' open='(' separator=', ' close=')'>#{item}</foreach> </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void inCustom() {
        Method method = chooseMethod(ConditionMapper.class, "inCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where>" +
                " <if test='collection != null'> AND `name` IN <foreach item= 'item'  collection='collection' open='(' separator=', ' close=')'>#{item}</foreach> </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notIn() {
        Method method = chooseMethod(ConditionMapper.class, "notIn");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name`" +
                " FROM t_condition" +
                " <where>" +
                " `name` NOT IN <foreach item= 'item'  collection='collection' open='(' separator=', ' close=')'>#{item}</foreach>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notInDynamic() {
        Method method = chooseMethod(ConditionMapper.class, "notInDynamic");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script> SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where>" +
                " <if test='collection != null'> AND `name` NOT IN <foreach item= 'item'  collection='collection' open='(' separator=', ' close=')'>#{item}</foreach> </if>" +
                " </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notInCustom() {
        Method method = chooseMethod(ConditionMapper.class, "notInCustom");
        MethodMeta methodMeta = annotationAssistant.parseMethodMate(method,
                tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        String expected = "<script>" +
                " SELECT `id`, `orgCode`, `orgName`, `name` FROM t_condition" +
                " <where>" +
                " <if test='collection != null'> AND `name` NOT IN <foreach item= 'item'  collection='collection' open='(' separator=', ' close=')'>#{item}</foreach> </if>" +
                " </where>" +
                "</script>";
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
