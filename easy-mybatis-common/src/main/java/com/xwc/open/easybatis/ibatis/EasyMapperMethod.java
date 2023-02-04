package com.xwc.open.easybatis.ibatis;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/4 15:49
 */
public class EasyMapperMethod extends MapperMethod {

    public EasyMapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
        super(mapperInterface, method, config);
    }

    @Override
    public Object execute(SqlSession sqlSession, Object[] args) {
        return super.execute(sqlSession, args);
    }
}
