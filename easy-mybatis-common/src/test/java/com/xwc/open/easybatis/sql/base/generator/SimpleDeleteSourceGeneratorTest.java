package com.xwc.open.easybatis.sql.base.generator;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.supports.DefaultSqlSourceGenerator;
import com.xwc.open.easybatis.supports.SqlSourceGenerator;
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
 * 类描述：用于测试简单的查询
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 16:01
 */
public class SimpleDeleteSourceGeneratorTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasyBatisConfiguration easyBatisConfiguration;
    SqlSourceGenerator sourceGenerator;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easyBatisConfiguration = new EasyBatisConfiguration(configuration);
        this.sourceGenerator = new DefaultSqlSourceGenerator(easyBatisConfiguration);
    }

    @Test
    public void simpleDel() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "del";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  DELETE FROM t_user WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }

    @Test
    public void simpleDynamicDel() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicDel";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  DELETE FROM t_user <where>  <if test='name != null'> AND `name` = #{name} </if>  <if test='id != null'> AND `id` = #{id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }

    @Test
    public void simpleDelObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "delObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  DELETE FROM t_user WHERE `id` = #{id} AND `org_code` = #{orgCodeAlias} AND `org_name` = #{orgName} AND `name` = #{name} </script>";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }

    @Test
    public void simpleDelDynamicObjectIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "delDynamicObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  DELETE FROM t_user <where>  <if test='object.id != null'> AND `id` = #{object.id} </if>  <if test='object.orgCodeAlias != null'> AND `org_code` = #{object.orgCodeAlias} </if>  <if test='object.orgName != null'> AND `org_name` = #{object.orgName} </if>  <if test='object.name != null'> AND `name` = #{object.name} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }


    public void simple() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }


}
