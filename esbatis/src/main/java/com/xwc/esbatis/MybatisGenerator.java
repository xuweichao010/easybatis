package com.xwc.esbatis;

import com.xwc.esbatis.assistant.GeneratorMapperAnnotationBuilder;
import com.xwc.esbatis.assistant.Reflection;
import com.xwc.esbatis.intercepts.TestIntercepts;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:11
 * 业务：
 * 功能：
 */
@Configuration
public class MybatisGenerator implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MybatisGenerator.class);

    @Autowired(required = false)
    private List<MapperFactoryBean> factoryBeans = new ArrayList<>();


    //注入spring容器
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    //spring容器刷新完成
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        org.apache.ibatis.session.Configuration configuration = null;
        for (MapperFactoryBean bean : factoryBeans) {
            Class<?> ec = Reflection.getEntityClass(bean.getMapperInterface());
            GeneratorMapperAnnotationBuilder parser;
            if (configuration == null) {
                configuration = bean.getSqlSession().getConfiguration();
                configuration.setMapUnderscoreToCamelCase(true);
                configuration.addInterceptor(new TestIntercepts());
            }
            if (ec != null) {
                parser = new GeneratorMapperAnnotationBuilder(configuration, bean.getMapperInterface(), ec);
                parser.parse();
            }
        }

    }
}
