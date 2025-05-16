package cn.onetozero.easybatis.ibatis;

import cn.onetozero.easybatis.EasyBatisConfiguration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/4 15:35
 */
public class EasyMapperProxyFactory<T>  {
    private final Class<T> mapperInterface;
    private final EasyBatisConfiguration easyBatisConfiguration;

    private final Map<Method, EasyMapperProxy.MapperMethodInvoker> methodCache = new ConcurrentHashMap<>();

    public EasyMapperProxyFactory(Class<T> mapperInterface, EasyBatisConfiguration easyBatisConfiguration) {
        this.mapperInterface = mapperInterface;
        this.easyBatisConfiguration = easyBatisConfiguration;
    }

    public Class<T> getMapperInterface() {
        return mapperInterface;
    }

    public Map<Method, EasyMapperProxy.MapperMethodInvoker> getMethodCache() {
        return methodCache;
    }

    @SuppressWarnings("unchecked")
    protected T newInstance(EasyMapperProxy<T> mapperProxy) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

    public T newInstance(SqlSession sqlSession) {
        final EasyMapperProxy<T> mapperProxy = new EasyMapperProxy<>(sqlSession, easyBatisConfiguration,
                mapperInterface, methodCache);
        return newInstance(mapperProxy);
    }
}
