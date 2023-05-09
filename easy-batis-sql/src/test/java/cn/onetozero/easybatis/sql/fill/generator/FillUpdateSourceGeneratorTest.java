package cn.onetozero.easybatis.sql.fill.generator;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.utils.Reflection;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.mapper.FillSourceGeneratorMapper;
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
public class FillUpdateSourceGeneratorTest {

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
    public void fillUpdate() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "update";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `org_code`=#{orgCode}, `org_name`=#{orgName}, `name`=#{name}, `data_type`=#{dataType}, `age`=#{age}, `job`=#{job}, `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void fillUpdateBatch() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "updateBatch";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  <foreach item='item' index='index' collection='collection' separator=';' > UPDATE t_user <set> `org_code`=#{item.orgCode}, `org_name`=#{item.orgName}, `name`=#{item.name}, `data_type`=#{item.dataType}, `age`=#{item.age}, `job`=#{item.job}, `update_time`=#{item.updateTime}, `update_id`=#{item.updateId}, `update_name`=#{item.updateName}, </set> WHERE `id` = #{item.id} </foreach> </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void fillUpdateDynamic() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "updateDynamic";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='orgCode != null'> `org_code`=#{orgCode}, </if>  <if test='orgName != null'> `org_name`=#{orgName}, </if>  <if test='name != null'> `name`=#{name}, </if>  <if test='dataType != null'> `data_type`=#{dataType}, </if>  <if test='age != null'> `age`=#{age}, </if>  <if test='job != null'> `job`=#{job}, </if> `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


    @Test
    public void fillUpdateParam() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "updateParam";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `name`=#{name}, `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void fillUpdateParamDynamic() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "updateParamDynamic";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='name != null'> `name`=#{name}, </if>  <if test='ageAlias != null'> `age`=#{ageAlias}, </if> `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, </set> WHERE `id` = #{id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


    @Test
    public void fillUpdateObject() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "updateObjectIgnore";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set> `org_code`=#{object.orgCode}, `org_name`=#{object.orgName}, `name`=#{object.name}, `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, </set> WHERE `id` = #{object.id} </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }

    @Test
    public void fillDynamicUpdateObject() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "dynamicUpdateObject";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='object.orgCode != null'> `org_code`=#{object.orgCode}, </if>  <if test='object.orgName != null'> `org_name`=#{object.orgName}, </if>  <if test='object.name != null'> `name`=#{object.name}, </if> `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, </set> <where>  <if test='object.id != null'> AND `id` = #{object.id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


    @Test
    public void fillDynamicUpdateMixture() {
        Class<?> interfaceClass = FillSourceGeneratorMapper.class;
        String methodName = "dynamicUpdateMixture";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script>  UPDATE t_user <set>  <if test='object.orgCode != null'> `org_code`=#{object.orgCode}, </if>  <if test='object.orgName != null'> `org_name`=#{object.orgName}, </if>  <if test='object.name != null'> `name`=#{object.name}, </if> `update_time`=#{updateTime}, `update_id`=#{updateId}, `update_name`=#{updateName}, </set> <where>  <if test='name != null'> AND `name` = #{name} </if>  <if test='object.id != null'> AND `id` = #{object.id} </if> </where> </script>";
        Assert.assertEquals(expected, sourceGenerator.update(operateMethodMeta));
    }


}
