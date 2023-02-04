package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.MapperEasyAnnotationBuilder;

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
                knownMappers.put(type, new EasyMapperProxyFactory<>(type));
                MapperEasyAnnotationBuilder parser = new MapperEasyAnnotationBuilder(config, type);
                parser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                }
            }
        }
    }
}
