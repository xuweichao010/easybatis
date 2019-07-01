package com.xwc.esbatis.intercepts;

import com.xwc.esbatis.anno.enums.SqlOperationType;
import com.xwc.esbatis.assistant.GeneratorMapperAnnotationBuilder;
import com.xwc.esbatis.meta.ColumMate;
import com.xwc.esbatis.meta.FieldType;
import com.xwc.esbatis.meta.MethodMate;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        MappedStatement ms = (MappedStatement) metaObject.getValue("parameterHandler.mappedStatement");
        MethodMate methodMate = GeneratorMapperAnnotationBuilder.get(ms.getId());
        if (methodMate == null) return invocation.proceed();
        if(methodMate.getOperationType() == SqlOperationType.BASE_PARAM_UPDATE) return invocation.proceed();
        Object value = metaObject.getValue("parameterHandler.parameterObject");
        if (methodMate.getEntityMate().getAudit().isEmpty()) return invocation.proceed();
        audit(value, methodMate);
        metaObject.setValue("parameterHandler.parameterObject",value);
        return invocation.proceed();
    }

    private Object audit(Object value, MethodMate methodMate) {
        if (value instanceof Map) {

        } else {
            setObject(value,methodMate,false);
        }

        return value;
    }

    private void setObject(Object value, MethodMate methodMate,boolean update) {
        Map<FieldType, ColumMate> audit = methodMate.getEntityMate().getAudit();
        audit.forEach((k,v)->{
            try{
                switch (k){
                    case CREATE_ID:
                        if(update)break;
                        v.getSetter().invoke(value,new Integer(1));
                        break;
                    case CREATE_NAME:
                        if(update)break;
                        v.getSetter().invoke(value,"徐卫超");
                        break;
                    case CREATE_TIME:
                        if(update)break;
                        v.getSetter().invoke(value,new Date());
                        break;
                    case UPDATE_ID:
                        v.getSetter().invoke(value,new Integer(2));
                        break;
                    case UPDATE_TIME:
                        v.getSetter().invoke(value,new Date());
                        break;
                    case UPDATE_NAME:
                        v.getSetter().invoke(value,"徐卫超2");
                        break;

                }
            }catch (Exception e){
                e.printStackTrace();
            }

        });
    }



    @Override
    public Object plugin(Object target) {
        System.out.println(" TestIntercepts -> plugin " + target);
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
