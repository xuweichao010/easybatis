package com.xwc.open.easybatis.ibatis;

import org.apache.ibatis.binding.MapperProxy;
import org.apache.ibatis.binding.MapperProxyFactory;
import org.apache.ibatis.session.SqlSession;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/4 15:35
 */
public class EasyMapperProxyFactory<T> extends MapperProxyFactory<T> {
    public EasyMapperProxyFactory(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    protected T newInstance(MapperProxy<T> mapperProxy) {
        return super.newInstance(mapperProxy);
    }

    @Override
    public T newInstance(SqlSession sqlSession) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>(sqlSession, this.getMapperInterface(), this.getMethodCache());
        return newInstance(mapperProxy);
    }
}
