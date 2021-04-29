package com.xwc.open.easybatis.spring.boot;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.support.EasyMapper;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class EasybatisBeanPostProcess implements BeanPostProcessor {
    private EasybatisConfiguration easybatisConfiguration;


    public EasybatisBeanPostProcess(EasybatisConfiguration easybatisConfiguration) {
        this.easybatisConfiguration = easybatisConfiguration;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MapperFactoryBean) {
            MapperFactoryBean<?> mapperFactoryBean = (MapperFactoryBean<?>) bean;
            if (EasyMapper.class.isAssignableFrom(mapperFactoryBean.getMapperInterface())) {
                easybatisConfiguration.addMapper(mapperFactoryBean.getMapperInterface());
            }
        }
        return bean;
    }
}
