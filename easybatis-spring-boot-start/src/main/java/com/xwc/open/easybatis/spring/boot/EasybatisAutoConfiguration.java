package com.xwc.open.easybatis.spring.boot;


import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.support.AuditorContext;

import com.xwc.open.easybatis.spring.EasybatisProperties;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class EasybatisAutoConfiguration {


    @Bean
    public EasybatisConfiguration easybatisConfiguration(EasybatisProperties properties,
                                                         SqlSessionFactory sqlSessionFactory, AuditorContext auditorContext) {
        EasybatisConfiguration config = new EasybatisConfiguration(sqlSessionFactory.getConfiguration());
        config.setAuditorContext(auditorContext);
        config.setIdType(properties.getIdType());
        config.setTablePrefix(properties.getTablePrefix());
        config.setGeneratorSqlLogger(properties.isGeneratorSqlLogger());
        return config;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuditorContext auditorContext() {
        return new DefaultAuditorContext();
    }

    @Bean
    public BeanPostProcessor easybatisBeanPostProcess(EasybatisConfiguration easybatisConfiguration) {
        return new EasybatisBeanPostProcess(easybatisConfiguration);
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis.easybatis")
    public EasybatisProperties easybatisProperties() {
        return new EasybatisProperties();

    }


}
