package com.xwc.open.easybatis.mysql;

import com.xwc.open.easybatis.core.AnnotationAssistant;
import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.mybatis.UserMapper;
import com.xwc.open.easybatis.mysql.model.MysqlSqlSourceGeneratorMapper;
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
 * 作者：徐卫超 cc
 * 时间：2020/12/18
 * 描述：mysql单元测试
 */
public class MysqlSqlSourceGeneratorTest {


    SqlSessionFactory sqlSessionFactory;
    Configuration configuration;
    EasybatisConfiguration easybatisConfiguration;
    TableMeta tableMeta;
    AnnotationAssistant annotationAssistant;


    @Before
    public void before() throws IOException {
        String resource = "mybatis.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        this.configuration = this.sqlSessionFactory.getConfiguration();
        this.easybatisConfiguration = new EasybatisConfiguration(configuration);
        easybatisConfiguration.addMapper(UserMapper.class);
        this.annotationAssistant = easybatisConfiguration.getAnnotationAssistant();
        this.tableMeta = annotationAssistant.parseEntityMate(Reflection.getEntityClass(MysqlSqlSourceGeneratorMapper.class));


    }

    @Test
    public void test() {
        Method[] declaredMethods = MysqlSqlSourceGeneratorMapper.class.getDeclaredMethods();
        for (Method method : declaredMethods) {
            MethodMeta methodMeta = annotationAssistant.parseSelectMethodMate(method, tableMeta,
                    AnnotationUtils.findAnnotation(method, SelectSql.class));
            if (method.getName().equals("findAll")) {
                this.findAll(methodMeta);
            } else if (method.getName().equals("findAll1")) {
                this.findAll1(methodMeta);
            } else if (method.getName().equals("selectKey")) {
                this.selectKey(methodMeta);
            }
        }

    }

    private void selectKey(MethodMeta methodMeta) {
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        System.out.println(select);
    }

    private void findAll1(MethodMeta methodMeta) {
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        System.out.println(select);
        Assert.assertEquals("<script> SELECT id FROM t_mysql_sql_source</script>", select);
    }

    public void findAll(MethodMeta methodMeta) {
        String select = easybatisConfiguration.getSqlSourceGenerator().select(methodMeta);
        System.out.println(select);
        Assert.assertEquals("<script> SELECT `id`,`orgCode`,`orgName`,`name` FROM t_mysql_sql_source</script>", select);
    }
}
