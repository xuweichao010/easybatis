package com.xwc.open.easybatis.sql.logic.generator;

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
public class LogicUpdateSourceGeneratorTest {

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
    public void logicUpdate() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "update";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `org_code`=#{orgCode}, `org_name`=#{orgName}, `name`=#{name}, `data_type`=#{dataType}, `age`=#{age}, `job`=#{job}, `create_time`=#{createTime}, `create_id`=#{createId}, `create_name`=#{createName}, `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, `valid`=#{valid}, </set> WHERE `id` = #{id} AND `valid` = #{valid} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void logicUpdateParam() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "updateParam";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `name`=#{name}, </set> WHERE `id` = #{id} AND `valid` = #{valid} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void logicInsertBatchIgnore() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "updateObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `org_code`=#{object.orgCode}, `org_name`=#{object.orgName}, `name`=#{object.name}, </set> WHERE `id` = #{object.id} AND `valid` = #{valid} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

//    @Test
//    public void logicUpdateObject() {
//        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
//        String methodName = "updateObject";
//        Method method = Reflection.chooseMethod(interfaceClass, methodName);
//        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
//                .getOperateMethodMeta(interfaceClass, method);
//        String expected = "";
//        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
//    }



}
