package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.update.BaseUpdateMapper;
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
public class BaseUpdateTest {

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
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(BaseUpdateMapper.class));
    }

    @Test
    public void update() {
        Method method = chooseMethod(BaseUpdateMapper.class, "update");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        String targetSql = "<script>" +
                " UPDATE t_mysql_sql_source" +
                " <set> `orgCode` = #{orgCode}, `orgName` = #{orgName}, `name` = #{name}, </set>" +
                " <where> `id` = #{id} </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void updateEntity() {
        Method method = chooseMethod(BaseUpdateMapper.class, "updateEntity");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        String targetSql = "<script>" +
                " UPDATE t_mysql_sql_source" +
                " <set> `orgCode` = #{orgCode}, `orgName` = #{orgName}, `name` = #{name}, </set>" +
                " <where> `id` = #{id} </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }


    @Test
    public void updateEntityMixture() {
        Method method = chooseMethod(BaseUpdateMapper.class, "updateEntityMixture");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String select = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        String targetSql = "<script>" +
                " UPDATE t_mysql_sql_source" +
                " <set> `orgCode` = #{entity.orgCode}, `orgName` = #{entity.orgName}, `name` = #{entity.name}, </set>" +
                " <where> `id` = #{entity.id} </where>" +
                "</script>";
        Assert.assertEquals(select, targetSql);
    }

    @Test
    public void updateParam() {
        Method method = chooseMethod(BaseUpdateMapper.class, "updateParam");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        String targetSql = "<script>" +
                " UPDATE t_mysql_sql_source" +
                " <set> `orgCode` = #{orgCode}, `orgName` = #{orgName}, </set>" +
                "</script>";
        Assert.assertEquals(sql, targetSql);
    }

    @Test
    public void updateParamCondition() {
        Method method = chooseMethod(BaseUpdateMapper.class, "updateParamCondition");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        String targetSql = "<script>" +
                " UPDATE t_mysql_sql_source" +
                " <set> `orgCode` = #{orgCode}, `orgName` = #{orgName}, </set>" +
                " <where> `id` = #{id} </where>" +
                "</script>";
        Assert.assertEquals(sql, targetSql);
    }

    @Test
    public void updateEntityDynamic() {
        Method method = chooseMethod(BaseUpdateMapper.class, "updateEntityDynamic");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        String targetSql = "<script>" +
                " UPDATE t_mysql_sql_source" +
                " <set>" +
                " <if test='orgCode != null'> `orgCode` = #{orgCode},</if>" +
                " <if test='orgName != null'> `orgName` = #{orgName},</if>" +
                " <if test='name != null'> `name` = #{name},</if>" +
                " </set>" +
                " <where> `id` = #{id} </where></script>";
        Assert.assertEquals(sql, targetSql);
    }

    @Test
    public void updateByCondition(){
        Method method = chooseMethod(BaseUpdateMapper.class, "updateByCondition");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        System.out.println(sql);
    }

    @Test
    public void updateKey(){
        Method method = chooseMethod(BaseUpdateMapper.class, "updateKey");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        System.out.println(sql);
    }


    @Test
    public void updateEntityDynamicMixture() {
        Method method = chooseMethod(BaseUpdateMapper.class, "updateEntityDynamicMixture");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String sql = easybatisConfiguration.getSqlSourceGenerator().update(meta);
        String targetSql = "<script>" +
                " UPDATE t_mysql_sql_source" +
                " <set>" +
                " <if test='entity.orgCode != null'> `orgCode` = #{entity.orgCode},</if>" +
                " <if test='entity.orgName != null'> `orgName` = #{entity.orgName},</if>" +
                " <if test='entity.name != null'> `name` = #{entity.name},</if>" +
                " </set>" +
                " <where> `id` = #{entity.id} </where>" +
                "</script>";
       Assert.assertEquals(targetSql,sql );
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
