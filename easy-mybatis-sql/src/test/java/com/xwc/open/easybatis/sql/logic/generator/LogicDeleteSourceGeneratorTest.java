package com.xwc.open.easybatis.sql.logic.generator;

import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.mapper.LogicSourceGeneratorMapper;
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
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/4 10:17
 */
public class LogicDeleteSourceGeneratorTest {

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
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
        this.sourceGenerator = new DefaultSqlSourceGenerator(easyBatisConfiguration);
    }

    @Test
    public void logicDel() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "del";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `valid`=#{valid0}, </set> WHERE `id` = #{id} AND `valid` = #{valid} </script>";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }

    @Test
    public void logicDelObject() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "delObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `valid`=#{valid0}, </set> WHERE `id` = #{object.id} AND `org_code` = #{object.orgCodeAlias} AND `org_name` = #{object.orgName} AND `name` = #{object.name} AND `valid` = #{valid} </script>";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }


    @Test
    public void logicDelDynamicObjectIgnore() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "delDynamicObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `valid`=#{valid0}, </set> <where>  <if test='object.id != null'> AND `id` = #{object.id} </if>  <if test='object.orgCodeAlias != null'> AND `org_code` = #{object.orgCodeAlias} </if>  <if test='object.orgName != null'> AND `org_name` = #{object.orgName} </if>  <if test='object.name != null'> AND `name` = #{object.name} </if> AND `valid` = #{valid} </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.delete(operateMethodMeta));
    }


}
