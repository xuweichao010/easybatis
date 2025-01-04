package cn.onetozero.easybatis.sql.logic.generator;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.utils.Reflection;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.mapper.LogicSourceGeneratorMapper;
import cn.onetozero.easybatis.supports.DefaultSqlSourceGenerator;
import cn.onetozero.easybatis.supports.SqlSourceGenerator;
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
public class LogicOffSelectSourceGeneratorTest {

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
    public void logicFindOne() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "findOneLogicOff";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =
                easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant().getOperateMethodMeta(interfaceClass, method);
        String expected =
                "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`," +
                        "`create_id`,`create_name`,`update_time`,`update_id`,`update_name` FROM t_user WHERE `id` = " +
                        "#{id} " + "</script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void logicFindOneDynamicIgnore() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "findOneLogicOffDynamicIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =
                easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant().getOperateMethodMeta(interfaceClass, method);
        String expected =
                "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`," +
                        "`create_id`,`create_name`,`update_time`,`update_id`,`update_name` FROM t_user <where>  <if " +
                        "test='id " + "!= null'> AND `id` = #{id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void logicFindAll() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "findLogicOffAll";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =
                easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant().getOperateMethodMeta(interfaceClass, method);
        String expected =
                "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`," +
                        "`create_id`,`create_name`,`update_time`,`update_id`,`update_name` FROM t_user WHERE `valid` " +
                        "= " + "#{valid} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }

    @Test
    public void logicQueryObject() {
        Class<?> interfaceClass = LogicSourceGeneratorMapper.class;
        String methodName = "queryObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta =
                easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant().getOperateMethodMeta(interfaceClass, method);
        String expected =
                "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`," +
                        "`create_id`,`create_name`,`update_time`,`update_id`,`update_name` FROM t_user <where> AND " +
                        "`org_code`" + " = #{query.orgCode} AND `org_name` = #{query.orgName}  <if test='query.name " +
                        "!= null'> AND `name` = " + "#{query.name} </if> AND `age` BETWEEN #{query.age} AND #{query" +
                        ".ageTo}  <if test='query.job != null'>" + " AND `job` = #{query.job} </if> AND `valid` = " +
                        "#{valid} </where> LIMIT #{query.limit} OFFSET #{query" + ".offset} </script>";
        Assert.assertEquals(expected, sourceGenerator.select(operateMethodMeta));
    }


}
