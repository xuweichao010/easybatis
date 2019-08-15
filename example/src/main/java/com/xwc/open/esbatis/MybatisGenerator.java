package com.xwc.open.esbatis;

import com.xwc.open.esbatis.assistant.EsbatisMapperAnnotationBuilder;
import com.xwc.open.esbatis.assistant.Reflection;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Collection;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:11
 * 业务：
 * 功能：
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties
public class MybatisGenerator implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private ApplicationContext applicationContext;
    private Configuration configuration;


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //AnnotationAssistan(esbatisProperties());
        this.configuration = applicationContext.getBean(SqlSessionFactory.class).getConfiguration();
        Collection<Class<?>> mappers = configuration.getMapperRegistry().getMappers();
        for (Class<?> clazz : mappers) {
            Class<?> ec = Reflection.getEntityClass(clazz);
            EsbatisMapperAnnotationBuilder parser = new EsbatisMapperAnnotationBuilder(configuration, clazz, ec);
            parser.parse();
        }
    }

    @Bean
    @ConfigurationProperties(prefix = "esbatis")
    public EsbatisProperties esbatisProperties() {
        return new EsbatisProperties();
    }
}
