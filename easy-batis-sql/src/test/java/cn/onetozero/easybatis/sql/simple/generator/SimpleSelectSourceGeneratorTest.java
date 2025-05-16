package cn.onetozero.easybatis.sql.simple.generator;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.utils.Reflection;
import cn.onetozero.easybatis.supports.DefaultSqlSourceGenerator;
import cn.onetozero.easybatis.supports.SqlSourceGenerator;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.mapper.SimpleSourceGeneratorMapper;
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
 * @author  徐卫超 (cc)
 * @since 2023/1/16 16:01
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
        this.easyBatisConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
        this.sourceGenerator = new DefaultSqlSourceGenerator(easyBatisConfiguration);
    }

    @Test
    public void simpleFindAll() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "findAll";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


    @Test
    public void simpleQueryObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where> AND `org_code` = #{orgCode} AND `org_name` = #{orgName}  <if test='name != null'> AND `name` = #{name} </if> AND `age` BETWEEN #{age} AND #{ageTo}  <if test='job != null'> AND `job` = #{job} </if> </where> LIMIT #{limit} OFFSET #{offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleQueryObjectIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where> AND `org_code` = #{query.orgCode} AND `org_name` = #{query.orgName}  <if test='query.name != null'> AND `name` = #{query.name} </if> AND `age` BETWEEN #{query.age} AND #{query.ageTo}  <if test='query.job != null'> AND `job` = #{query.job} </if> </where> LIMIT #{query.limit} OFFSET #{query.offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


    @Test
    public void simpleDynamicQueryObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicQueryObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where>  <if test='orgCode != null'> AND `org_code` = #{orgCode} </if>  <if test='orgName != null'> AND `org_name` = #{orgName} </if>  <if test='name != null'> AND `name` = #{name} </if>  <if test='age != null and ageTo != null'> AND `age` BETWEEN #{age} AND #{ageTo} </if>  <if test='job != null'> AND `job` = #{job} </if> </where> LIMIT #{limit} OFFSET #{offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleDynamicQueryObjectIgnore() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "dynamicQueryObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where>  <if test='query.orgCode != null'> AND `org_code` = #{query.orgCode} </if>  <if test='query.orgName != null'> AND `org_name` = #{query.orgName} </if>  <if test='query.name != null'> AND `name` = #{query.name} </if>  <if test='query.age != null and query.ageTo != null'> AND `age` BETWEEN #{query.age} AND #{query.ageTo} </if>  <if test='query.job != null'> AND `job` = #{query.job} </if> </where> LIMIT #{query.limit} OFFSET #{query.offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


    @Test
    public void simpleQueryObjectCount() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObjectCount";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT  COUNT(*) FROM t_user <where> AND `org_code` = #{orgCode} AND `org_name` = #{orgName}  <if test='name != null'> AND `name` = #{name} </if> AND `age` BETWEEN #{age} AND #{ageTo}  <if test='job != null'> AND `job` = #{job} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleQueryObjectDistinct() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObjectDistinct";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT DISTINCT(age) FROM t_user <where> AND `org_code` = #{orgCode} AND `org_name` = #{orgName}  <if test='name != null'> AND `name` = #{name} </if> AND `age` BETWEEN #{age} AND #{ageTo}  <if test='job != null'> AND `job` = #{job} </if> </where> LIMIT #{limit} OFFSET #{offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void simpleQueryObjectCountDistinct() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObjectCountDistinct";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT COUNT(DISTINCT(age)) FROM t_user <where> AND `org_code` = #{orgCode} AND `org_name` = #{orgName}  <if test='name != null'> AND `name` = #{name} </if> AND `age` BETWEEN #{age} AND #{ageTo}  <if test='job != null'> AND `job` = #{job} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


    @Test
    public void simpleQueryMultiObject() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryMultiObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid` FROM t_user <where> AND `org_code` = #{query.orgCode} AND `org_name` = #{query.orgName}  <if test='query.name != null'> AND `name` = #{query.name} </if> AND `age` BETWEEN #{query.age} AND #{query.ageTo}  <if test='query.job != null'> AND `job` = #{query.job} </if> </where> LIMIT #{page.limit} OFFSET #{page.offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    public void simple() {
        Class<?> interfaceClass = SimpleSourceGeneratorMapper.class;
        String methodName = "queryObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =  easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


}
