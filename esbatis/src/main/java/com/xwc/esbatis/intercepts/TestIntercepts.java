package com.xwc.esbatis.intercepts;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Statement;
import java.util.Properties;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/13  10:07
 * 业务：
 * 功能：
 */
@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "parameterize",
        args = {Statement.class})})
public class TestIntercepts implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        //
        System.out.println(" TestIntercepts -> intercept "+ invocation.getMethod().getName());
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        //metaObject.getValue()
        Object proceed = invocation.proceed();
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        System.out.println(" TestIntercepts -> plugin "+target);
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
