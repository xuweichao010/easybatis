package com.xwc.open.easybatis.components;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.supports.DefaultEasyBatisSourceGenerator;
import com.xwc.open.easybatis.supports.EasyBatisSourceGenerator;
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
        String expected = "<script>  INSERT INTO t_user(`id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name`,`valid`) VALUES (#{id},#{orgCode},#{orgName},#{name},#{dataType},#{age},#{job},#{createTime},#{createId},#{createName},#{updateTime},#{updateId},#{updateName},#{valid}) </script>";
        Assert.assertEquals(expected, sourceGenerator.insert(operateMethodMeta));
    }

}
