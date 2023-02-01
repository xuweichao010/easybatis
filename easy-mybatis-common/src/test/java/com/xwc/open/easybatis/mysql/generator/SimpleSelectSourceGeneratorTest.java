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
public class SimpleSelectSourceGeneratorTest {

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
    public void simpleQueryObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where> AND `org_code` = #{orgCode} AND `org_name` = #{orgName}  <if test='name != null'> AND `name` = #{name} </if> AND `age` BETWEEN #{age} AND #{ageTo}  <if test='job != null'> AND `job` = #{job} </if> </where> LIMIT #{limit} OFFSET #{offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleQueryObjectIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where> AND `org_code` = #{query.orgCode} AND `org_name` = #{query.orgName}  <if test='query.name != null'> AND `name` = #{query.name} </if> AND `age` BETWEEN #{query.age} AND #{query.ageTo}  <if test='query.job != null'> AND `job` = #{query.job} </if> </where> LIMIT #{query.limit} OFFSET #{query.offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


    @Test
    public void simpleDynamicQueryObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicQueryObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where>  <if test='orgCode != null'> AND `org_code` = #{orgCode} </if>  <if test='orgName != null'> AND `org_name` = #{orgName} </if>  <if test='name != null'> AND `name` = #{name} </if>  <if test='age != null and ageTo != null'> AND `age` BETWEEN #{age} AND #{ageTo} </if>  <if test='job != null'> AND `job` = #{job} </if> </where> LIMIT #{limit} OFFSET #{offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleDynamicQueryObjectIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicQueryObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where>  <if test='query.orgCode != null'> AND `org_code` = #{query.orgCode} </if>  <if test='query.orgName != null'> AND `org_name` = #{query.orgName} </if>  <if test='query.name != null'> AND `name` = #{query.name} </if>  <if test='query.age != null and query.ageTo != null'> AND `age` BETWEEN #{query.age} AND #{query.ageTo} </if>  <if test='query.job != null'> AND `job` = #{query.job} </if> </where> LIMIT #{query.limit} OFFSET #{query.offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


    public void simple() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


}
