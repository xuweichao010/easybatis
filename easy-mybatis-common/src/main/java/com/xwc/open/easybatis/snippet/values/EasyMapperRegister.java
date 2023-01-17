package com.xwc.open.easybatis.snippet.values;

import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.MapperEasyAnnotationBuilder;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 17:59
 */
public class EasyMapperRegister {

    private final EasyBatisConfiguration config;
    private final HashSet<Class<?>> knownMappers = new HashSet<>();

    public EasyMapperRegister(EasyBatisConfiguration config) {
        this.config = config;
    }


    public <T> boolean hasMapper(Class<T> type) {
        return knownMappers.contains(type);
    }

    public <T> void addMapper(Class<T> type) {
        if (type.isInterface() && EasyMapper.class.isAssignableFrom(type)) {
            if (hasMapper(type)) {
                return;
            }
            boolean loadCompleted = false;
            try {
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
