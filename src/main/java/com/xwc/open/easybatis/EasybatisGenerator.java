package com.xwc.open.easybatis;

import com.xwc.open.easybatis.assistant.EasyBatisMapperAnnotationBuilder;
import com.xwc.open.easybatis.assistant.Reflection;
import com.xwc.open.easybatis.interfaces.EasyMapper;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Collection;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:11
 * 业务： EasyBatis的自动SQL创建器
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties
public class EasybatisGenerator implements ApplicationContextAware, EnvironmentAware, BeanPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EasybatisGenerator.class);

    private ApplicationContext applicationContext;
    private Configuration configuration;
    private Environment environment;


    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    /**
     * 初始化EasyBatis组件及解析注解信息并生成SQL
     *
     * @param mappers spring容器中所有的mapper对象
     */
    private void initEasybatis(Collection<Class<?>> mappers) {
        for (Class<?> clazz : mappers) {
            if (EasyMapper.class.isAssignableFrom(clazz)) {
                Class<?> ec = Reflection.getEntityClass(clazz);
                EasyBatisMapperAnnotationBuilder parser =
                        new EasyBatisMapperAnnotationBuilder(configuration, clazz, ec, new EasyBatisEnvironment(environment, configuration));
                parser.parse();
            }
        }
        logger.info("EasyBatis Finished initializing");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(beanName);
        return bean;
    }

    /**
     * 获取Spring的环境信息
     *
     * @param environment 环境对象
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


}
