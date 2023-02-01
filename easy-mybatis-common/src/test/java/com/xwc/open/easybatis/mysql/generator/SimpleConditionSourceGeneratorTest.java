package com.xwc.open.easybatis.mysql.generator;

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
public class SimpleConditionSourceGeneratorTest {

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
    public void simpleFindOne() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "findOne";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user WHERE 1 = 1 AND `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleFindOneDynamic() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "findOneDynamic";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where>  <if test='id != null'> AND `id` = #{id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleFindOneIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "findOneIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user WHERE 1 = 1 AND `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleFindOneDynamicIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "findOneDynamicIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where>  <if test='id != null'> AND `id` = #{id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleBetween() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "between";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user WHERE 1 = 1 AND `age` BETWEEN #{age} AND #{ageTo} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleBetweenIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "betweenIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user WHERE 1 = 1 AND `age` BETWEEN #{age} AND #{ageTo} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleBetweenDynamic() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "betweenDynamic";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where>  <if test='age != null and ageTo != null'> AND `age` BETWEEN #{age} AND #{ageTo} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

}
