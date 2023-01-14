package com.xwc.open.easybatis.components;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.utils.Reflection;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.components.mapper.SimpleSourceGeneratorMapper;
import com.xwc.open.easybatis.supports.DefaultEasyBatisSourceGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/14 10:47
 */
public class DefaultMyBatisSourceGeneratorTest {

    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasyBatisConfiguration easyBatisConfiguration;

    @Before
    public void before() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = sqlSessionFactory.getConfiguration();
        this.easyBatisConfiguration = new EasyBatisConfiguration(configuration);
    }

    @Test
    public void simpleInsertTest() {
        Method method = Reflection.chooseMethod(SimpleSourceGeneratorMapper.class, "simpleInsert");
        Class<?> entityClass = Reflection.getEntityClass(SimpleSourceGeneratorMapper.class);
        OperateMethodMeta simpleInsert = easyBatisConfiguration.getOperateMethodAssistant().getOperateMethodMeta(entityClass, method);
        DefaultEasyBatisSourceGenerator defaultEasyBatisSourceGenerator =
                new DefaultEasyBatisSourceGenerator(easyBatisConfiguration);
        defaultEasyBatisSourceGenerator.insert(simpleInsert);
    }


}
