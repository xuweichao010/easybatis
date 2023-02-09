package com.xwc.open.easybatis.sql.simple.generator;

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
public class SimpleUpdateSourceGeneratorTest {

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
    public void simpleUpdate() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "update";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `org_code`=#{orgCode}, `org_name`=#{orgName}, `name`=#{name}, `data_type`=#{dataType}, `age`=#{age}, `job`=#{job}, `create_time`=#{createTime}, `create_id`=#{createId}, `create_name`=#{createName}, `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, `valid`=#{valid}, </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void simpleUpdateDynamic() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "updateDynamic";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='orgCode != null'> `org_code`=#{orgCode}, </if>  <if test='orgName != null'> `org_name`=#{orgName}, </if>  <if test='name != null'> `name`=#{name}, </if>  <if test='dataType != null'> `data_type`=#{dataType}, </if>  <if test='age != null'> `age`=#{age}, </if>  <if test='job != null'> `job`=#{job}, </if>  <if test='createTime != null'> `create_time`=#{createTime}, </if>  <if test='createId != null'> `create_id`=#{createId}, </if>  <if test='createName != null'> `create_name`=#{createName}, </if>  <if test='updateTime != null'> `update_time`=#{updateTime}, </if>  <if test='updateId != null'> `update_id`=#{updateId}, </if>  <if test='updateName != null'> `update_name`=#{updateName}, </if>  <if test='valid != null'> `valid`=#{valid}, </if> </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


    @Test
    public void simpleUpdateParam() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "updateParam";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `name`=#{name}, </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void simpleUpdateParamDynamic() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "updateParamDynamic";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='name != null'> `name`=#{name}, </if>  <if test='ageAlias != null'> `age`=#{ageAlias}, </if> </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void simpleDynamicUpdateParam() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicUpdateParam";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='name != null'> `name`=#{name}, </if>  <if test='ageAlias != null'> `age`=#{ageAlias}, </if> </set> <where>  <if test='id != null'> AND `id` = #{id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


    @Test
    public void simpleUpdateObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "updateObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `org_code`=#{orgCode}, `org_name`=#{orgName}, `name`=#{name}, </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void simpleUpdateObjectIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "updateObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `org_code`=#{object.orgCode}, `org_name`=#{object.orgName}, `name`=#{object.name}, </set> WHERE `id` = #{object.id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void simpleDynamicUpdateObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicUpdateObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='orgCode != null'> `org_code`=#{orgCode}, </if>  <if test='orgName != null'> `org_name`=#{orgName}, </if>  <if test='name != null'> `name`=#{name}, </if> </set> <where>  <if test='id != null'> AND `id` = #{id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void simpleDynamicUpdateMixture() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicUpdateMixture";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='object.orgCode != null'> `org_code`=#{object.orgCode}, </if>  <if test='object.orgName != null'> `org_name`=#{object.orgName}, </if>  <if test='object.name != null'> `name`=#{object.name}, </if> </set> <where>  <if test='name != null'> AND `name` = #{name} </if>  <if test='object.id != null'> AND `id` = #{object.id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


    public void simple() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


}
