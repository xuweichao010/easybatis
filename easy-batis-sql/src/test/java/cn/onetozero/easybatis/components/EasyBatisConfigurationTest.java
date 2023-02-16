package cn.onetozero.easybatis.components;

import cn.onetozero.easy.parse.EasyConfiguration;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 10:02
 */
public class EasyBatisConfigurationTest {
    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = sqlSessionFactory.getConfiguration();
    }

    @Test
    public void create() {
        EasyBatisConfiguration easyConfiguration = new EasyBatisConfiguration(new EasyConfiguration());
    }
}
