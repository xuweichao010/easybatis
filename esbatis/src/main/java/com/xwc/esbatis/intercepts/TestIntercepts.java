package com.xwc.esbatis.intercepts;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;

import java.util.Properties;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/13  10:07
 * 业务：
 * 功能：
 */
@Intercepts({@Signature(
        type = Executor.class,
        method = "*",
        args = {MappedStatement.class, Object.class})})
public class TestIntercepts implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
