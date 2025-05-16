package cn.onetozero.easybatis.ibatis;

import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/16 17:59
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
        if (type.isInterface()) {
            if (hasMapper(type)) {
                return;
            }
            boolean loadCompleted = false;
            try {
                // 增强的mapper解析
                knownMappers.put(type, new EasyMapperProxyFactory<>(type, config));
                if (EasyMapper.class.isAssignableFrom(type)) {
                    MapperEasyAnnotationBuilder easyParser = new MapperEasyAnnotationBuilder(config, type);
                    easyParser.parse();
                }
                // It's important that the type is added before the parser is run
                // otherwise the binding may automatically be attempted by the
                // mapper parser. If the type is already known, it won't try.
                MapperAnnotationBuilder batisParser = new MapperAnnotationBuilder(config, type);
                batisParser.parse();
                loadCompleted = true;
            } finally {
                if (!loadCompleted) {
                    knownMappers.remove(type);
                }
            }
        }
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


    /**
     * Gets the mappers.
     *
     * @return the mappers
     * @since 3.2.2
     */
    public Collection<Class<?>> getMappers() {
        return Collections.unmodifiableCollection(knownMappers.keySet());
    }

    /**
     * Adds the mappers.
     *
     * @param packageName the package name
     * @param superType   the super type
     * @since 3.2.2
     */
    public void addMappers(String packageName, Class<?> superType) {
        ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
        resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
        Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
        for (Class<?> mapperClass : mapperSet) {
            addMapper(mapperClass);
        }
    }

    /**
     * Adds the mappers.
     *
     * @param packageName the package name
     * @since 3.2.2
     */
    public void addMappers(String packageName) {
        addMappers(packageName, Object.class);
    }
}
