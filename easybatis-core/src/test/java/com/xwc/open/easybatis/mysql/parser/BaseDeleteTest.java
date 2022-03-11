package com.xwc.open.easybatis.mysql.parser;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.mysql.parser.delete.BaseDeleteMapper;
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
public class BaseDeleteTest {


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
    public void delete() {
        Method method = chooseMethod(BaseDeleteMapper.class, "delete");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().delete(meta);
        String expected = "<script>" +
                " DELETE FROM t_mysql_sql_source" +
                " <where> `id` = #{id} </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteParam() {
        Method method = chooseMethod(BaseDeleteMapper.class, "deleteParam");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().delete(meta);
        String expected = "<script>" +
                " DELETE FROM t_mysql_sql_source" +
                " <where> `orgName` = #{orgName} </where>" +
                "</script>";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void deleteParamDynamic() {
        Method method = chooseMethod(BaseDeleteMapper.class, "deleteParamDynamic");
        MethodMeta meta = annotationAssistant.parseMethodMate(method, tableMeta);
        String actual = easybatisConfiguration.getSqlSourceGenerator().delete(meta);
        String expected = "<script>" +
                " DELETE FROM t_mysql_sql_source" +
                " <where> <if test='orgName != null'> AND `orgName` = #{orgName} </if> </where>" +
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
