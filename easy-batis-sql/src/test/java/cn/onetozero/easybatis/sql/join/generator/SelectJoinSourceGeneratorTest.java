package cn.onetozero.easybatis.sql.join.generator;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.utils.Reflection;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.mapper.JoinSourceGeneratorMapper;
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
 * 作者： 徐卫超
 * @since  2023/7/26 20:01
 * 描述：
 */
public class SelectJoinSourceGeneratorTest {

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
    public void joinUser() {
        Class<?> interfaceClass = JoinSourceGeneratorMapper.class;
        String methodName = "joinUser";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT t.* FROM t_user t INNER JOIN t_org o ON t.org_code = o.code  </script>";
        Assert.assertEquals(expected, sourceGenerator.selectJoin(operateMethodMeta));
    }

    @Test
    public void joinUserByCode() {
        Class<?> interfaceClass = JoinSourceGeneratorMapper.class;
        String methodName = "joinUserByCode";
        Method method = Reflection.chooseMethod(interfaceClass, methodName);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getEasyConfiguration().getOperateMethodAssistant()
                .getOperateMethodMeta(interfaceClass, method);
        String expected = "<script> SELECT `id`,`org_code`,`org_name`,`name`,`data_type`,`age`,`job`,`create_time`,`create_id`,`create_name`,`update_time`,`update_id`,`update_name` FROM t_user WHERE `id` = #{id} AND `valid` = #{valid} </script>";
//        Assert.assertEquals(expected, sourceGenerator.selectJoin(operateMethodMeta));
    }
}
