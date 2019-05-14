package com.xwc.esbatis.intercepts;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/13  10:07
 * 业务：
 * 功能：
 */
@Intercepts({@Signature(
        type = ParameterHandler.class,
        method = "*",
        args = {MappedStatement.class, Object.class})})
public class TestIntercepts implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("intercept.............");
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
