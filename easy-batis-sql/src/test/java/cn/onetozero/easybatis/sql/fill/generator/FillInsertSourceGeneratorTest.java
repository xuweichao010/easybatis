package cn.onetozero.easybatis.sql.fill.generator;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.utils.Reflection;
import cn.onetozero.easybatis.supports.DefaultSqlSourceGenerator;
import cn.onetozero.easybatis.supports.SqlSourceGenerator;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.mapper.FillSourceGeneratorMapper;
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
public class FillInsertSourceGeneratorTest {

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
    public void fillInsert() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "insert";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`) VALUES (#{id},#{orgCode},#{orgName},#{name},#{dataType},#{age},#{job},#{createTime},#{createId},#{createName},#{updateTime}) </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }


    @Test
    public void fillInsertIgnore() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "insertIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`) VALUES (#{user.id},#{user.orgCode},#{user.orgName},#{user.name},#{user.dataType},#{user.age},#{user.job},#{user.createTime},#{user.createId},#{user.createName},#{user.updateTime}) </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

    @Test
    public void simpleInsertBatch() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "insertBatch";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`) VALUES <foreach item='users' index='index' collection='collection' separator=',' > (#{users.id},#{users.orgCode},#{users.orgName},#{users.name},#{users.dataType},#{users.age},#{users.job},#{users.createTime},#{users.createId},#{users.createName},#{users.updateTime}) </foreach> </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

    @Test
    public void simpleInsertBatchIgnore() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "insertBatchIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`) VALUES <foreach item='users' index='index' collection='users' separator=',' > (#{users.id},#{users.orgCode},#{users.orgName},#{users.name},#{users.dataType},#{users.age},#{users.job},#{users.createTime},#{users.createId},#{users.createName},#{users.updateTime}) </foreach> </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

}
