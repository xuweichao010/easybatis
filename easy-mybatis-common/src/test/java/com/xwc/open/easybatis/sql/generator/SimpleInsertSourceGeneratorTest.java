package com.xwc.open.easybatis.sql.generator;

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
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 16:01
 */
public class SimpleInsertSourceGeneratorTest {

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
    public void simpleFindAll() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "findAll";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleInsertIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "insertIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid`) VALUES (#{user.id},#{user.orgCode},#{user.orgName},#{user.name},#{user.dataType},#{user.age},#{user.job},#{user.createTime},#{user.createId},#{user.createName},#{user.updateTime},#{user.updateId},#{user.updateName},#{user.valid}) </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

    @Test
    public void simpleInsertBatch() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "insertBatch";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid`) VALUES <foreach item='users' index='index' collection='collection' separator=',' > (#{users.id},#{users.orgCode},#{users.orgName},#{users.name},#{users.dataType},#{users.age},#{users.job},#{users.createTime},#{users.createId},#{users.createName},#{users.updateTime},#{users.updateId},#{users.updateName},#{users.valid}) </foreach> </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

    @Test
    public void simpleInsertBatchIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "insertBatchIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid`) VALUES <foreach item='users' index='index' collection='users' separator=',' > (#{users.id},#{users.orgCode},#{users.orgName},#{users.name},#{users.dataType},#{users.age},#{users.job},#{users.createTime},#{users.createId},#{users.createName},#{users.updateTime},#{users.updateId},#{users.updateName},#{users.valid}) </foreach> </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }
}
