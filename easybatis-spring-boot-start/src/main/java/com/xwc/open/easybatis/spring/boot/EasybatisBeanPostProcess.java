package com.xwc.open.easybatis.spring.boot;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.support.EasyMapper;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class EasybatisBeanPostProcess implements BeanPostProcessor {
    private EasybatisConfiguration easybatisConfiguration;

    public EasybatisBeanPostProcess(EasybatisConfiguration easybatisConfiguration) {
        this.easybatisConfiguration = easybatisConfiguration;
    }

    @Override
    @SuppressWarnings("all")
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if ( bean instanceof MapperFactoryBean) {
            MapperFactoryBean mfb = (MapperFactoryBean) bean;
            if (EasyMapper.class.isAssignableFrom(mfb.getMapperInterface())) {
                doPostProcessAfterInitialization(mfb.getMapperInterface());
            }
        }
        return bean;
    }

    private void doPostProcessAfterInitialization(Class<?> clazz) {
        easybatisConfiguration.addMapper(clazz);
    }
}
