package com.xwc.open.easybatis;

import com.xwc.open.easybatis.assistant.EasybatisMapperAnnotationBuilder;
import com.xwc.open.easybatis.assistant.Reflection;
import com.xwc.open.easybatis.handler.impl.EnumTypeHandler;
import com.xwc.open.easybatis.interfaces.EasyMapper;
import com.xwc.open.easybatis.plugin.EasybatisPlugin;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
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
public class EasybatisGenerator implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(EasybatisGenerator.class);
    private static boolean easybatisInit = true;

    private ApplicationContext applicationContext;
    private Configuration configuration;

    @Bean
    public EasybatisEnvironment easybatisEnvironment() {
        return new EasybatisEnvironment();
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.configuration = applicationContext.getBean(SqlSessionFactory.class).getConfiguration();
        Collection<Class<?>> mappers = configuration.getMapperRegistry().getMappers();
        if (!mappers.isEmpty() && easybatisInit) {
            easybatisInit = false;
            initEasybatis(configuration, mappers);
        }

    }

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.configuration = applicationContext.getBean(SqlSessionFactory.class).getConfiguration();
        Collection<Class<?>> mappers = configuration.getMapperRegistry().getMappers();
        if (!mappers.isEmpty() && easybatisInit) {
            easybatisInit = false;
            initEasybatis(configuration, mappers);
        }
    }

    private void initEasybatis(Configuration configuration, Collection<Class<?>> mappers) {
        for (Class<?> clazz : mappers) {
            if (EasyMapper.class.isAssignableFrom(clazz)) {
                Class<?> ec = Reflection.getEntityClass(clazz);
                EasybatisMapperAnnotationBuilder parser = new EasybatisMapperAnnotationBuilder(configuration, clazz, ec);
                parser.parse();
            }
        }
        configuration.setUseActualParamName(true);
        configuration.addInterceptor(new EasybatisPlugin());
        configuration.getTypeHandlerRegistry().setDefaultEnumTypeHandler(EnumTypeHandler.class);
        easybatisInit = true;
        logger.info("easybatis 初始化完毕");
    }
}
