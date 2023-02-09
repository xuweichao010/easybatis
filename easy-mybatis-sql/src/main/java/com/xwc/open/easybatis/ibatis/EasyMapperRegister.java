package com.xwc.open.easybatis.ibatis;

import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.session.SqlSession;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 17:59
 */
public class EasyMapperRegister {

    private final EasyBatisConfiguration config;
    private final Map<Class<?>, EasyMapperProxyFactory<?>> knownMappers = new HashMap<>();

    public EasyMapperRegister(EasyBatisConfiguration config) {
        this.config = config;
    }


    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.containsKey(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface() && EasyMapper.class.isAssignableFrom(type)) {
            if (hasMapper(type)) {
                return;
            }
            boolean loadCompleted = false;
            try {
                knownMappers.put(type, new EasyMapperProxyFactory<>(type, config));
                MapperEasyAnnotationBuilder easyParser = new MapperEasyAnnotationBuilder(config, type);
                easyParser.parse();
                // It's important that the type is added before the parser is run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                MapperAnnotationBuilder batisParser = new MapperAnnotationBuilder(config.getConfiguration(), type);
                batisParser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                }
            }
        }
    }

    /**
     * Gets the mappers.
     *
     * @return the mappers
     * @since 3.2.2
     */
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }


    @SuppressWarnings("unchecked")
    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        final EasyMapperProxyFactory<T> mapperProxyFactory = (EasyMapperProxyFactory<T>) knownMappers.get(type);
        if (mapperProxyFactory == null) {
            throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
        }
        try {
            return mapperProxyFactory.newInstance(sqlSession);
        } catch (Exception e) {
            throw new BindingException("Error getting mapper instance. Cause: " + e, e);
        }
    }
}
