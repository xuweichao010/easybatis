package com.xwc.open.easybatis.components;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.EasyBatisSourceGenerator;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.supports.DefaultEasyBatisSourceGenerator;
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
 * 时间 2023/1/16 16:01
 */
public class InsertSourceGeneratorTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasyBatisConfiguration easyBatisConfiguration;
    EasyBatisSourceGenerator sourceGenerator;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = sqlSessionFactory.getConfiguration();
        this.configuration.setMapUnderscoreToCamelCase(true);
        this.easyBatisConfiguration = new EasyBatisConfiguration(configuration);
        this.sourceGenerator = new DefaultEasyBatisSourceGenerator(easyBatisConfiguration);
    }


    @Test
    public void simpleInsert() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "insert";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`age`,`job`,`valid`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`) VALUES (#{id},#{orgCode},#{orgName},#{name},#{age},#{job},#{valid},#{createTime},#{createId},#{createName},#{updateTime},#{updateId},#{updateName}) </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

    @Test
    public void simpleInsertIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "insertIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`age`,`job`,`valid`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`) VALUES (#{user.id},#{user.orgCode},#{user.orgName},#{user.name},#{user.age},#{user.job},#{user.valid},#{user.createTime},#{user.createId},#{user.createName},#{user.updateTime},#{user.updateId},#{user.updateName}) </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

    @Test
    public void simpleInsertBatch() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "insertBatch";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`age`,`job`,`valid`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`) VALUES <foreach item='users' index='index' collection='collection' separator=',' > (#{users.id},#{users.orgCode},#{users.orgName},#{users.name},#{users.age},#{users.job},#{users.valid},#{users.createTime},#{users.createId},#{users.createName},#{users.updateTime},#{users.updateId},#{users.updateName}) </foreach> </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

    @Test
    public void simpleInsertBatchIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "insertBatchIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`age`,`job`,`valid`," +
                "`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`) VALUES <foreach item='users' index='index' collection='users' separator=',' > (#{users.id},#{users.orgCode},#{users.orgName},#{users.name},#{users.age},#{users.job},#{users.valid},#{users.createTime},#{users.createId},#{users.createName},#{users.updateTime},#{users.updateId},#{users.updateName}) </foreach> </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }
}
