package com.xwc.open.easybatis.core.plugin;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMeta;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.core.model.table.ColumnMeta;
import com.xwc.open.easybatis.core.model.table.IdMeta;
import com.xwc.open.easybatis.core.model.table.LoglicColumn;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class ExecutorHandlerInterceptor implements Interceptor {
    private static final String PARAM_OBJECT = "parameterHandler.parameterObject";
    private static final String MAPPED_STATEMENT = "parameterHandler.mappedStatement";
    private final EasybatisConfiguration easybatisConfiguration;

    public ExecutorHandlerInterceptor(EasybatisConfiguration easybatisConfiguration) {
        this.easybatisConfiguration = easybatisConfiguration;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        MethodMeta methodMeta = easybatisConfiguration.getMethodMeta(ms.getId());
        if (methodMeta == null) {
            return invocation.proceed();
        }
        Object easybatisValue = doIntercept(methodMeta, args[1], ms);
        args[1] = easybatisValue;
        return invocation.proceed();
    }

    public Object doIntercept(MethodMeta methodMeta, Object value, MappedStatement ms) {
        if (methodMeta.getSqlCommand() == SqlCommandType.SELECT) {
            return doInterceptSelect(methodMeta, value, ms);
        } else if (methodMeta.getSqlCommand() == SqlCommandType.INSERT) {
            return doInterceptInsert(methodMeta, value);
        }
        return null;
    }

    private Object doInterceptInsert(MethodMeta methodMeta, Object value) {
        TableMeta tableMetadata = methodMeta.getTableMetadata();
        if (value instanceof List) {
            ((List<?>) value).forEach(item -> {
                invokeObject(item, tableMetadata);
            });
        } else if (value instanceof Map) {
            Map<String, Object> map = (Map) value;
            map.forEach((key, mapValue) -> {
                if (key.startsWith("param")) {
                    return;
                }
                if (mapValue instanceof List) {
                    ((List<?>) mapValue).forEach(item -> {
                        invokeObject(item, tableMetadata);
                    });
                } else {
                    invokeObject(mapValue, tableMetadata);
                }
            });
        } else {
            invokeObject(value, tableMetadata);
        }
        return value;
    }

    private void invokeMethod(Object object, Method setterMethod, Object setterValue) {
        try {
            setterMethod.invoke(object, setterValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            //todo 打印一个警告日志
        }
    }

    private void invokeObject(Object node, TableMeta tableMetadata) {
        if (tableMetadata.isSource(node.getClass())) {
            LoglicColumn logic = tableMetadata.getLogic();
            if (logic != null) {
                invokeMethod(node, logic.getSetter(), logic.getValid());
            }
        }
    }

    private void invokeParam(TableMeta tableMetadata, Map<String, Object> paramMap, List<ParameterMapping> list) {
        LoglicColumn logic = tableMetadata.getLogic();
        if (logic != null) {
            paramMap.put(logic.getField(), logic.getValid());
            //invokeMapping(logic, list);
        }
    }

    private void invokeMapping(ColumnMeta columnMeta, List<ParameterMapping> list) {
        ParameterMapping parameterMapping = new ParameterMapping.Builder(
                easybatisConfiguration.getMybatisConfiguration(),
                columnMeta.getField(),
                columnMeta.getSource().getType()
        ).build();
        list.add(parameterMapping);
    }

    @SuppressWarnings("all")
    private Object doInterceptSelect(MethodMeta methodMeta, Object value, MappedStatement ms) {
        Map<String, Object> paramMap;
        TableMeta tableMetadata = methodMeta.getTableMetadata();
        if (value instanceof Map) {
            paramMap = (Map<String, Object>) value;
        } else {
            paramMap = new HashMap<>();
            ParamMeta paramMeta = methodMeta.singleParam();
            if (methodMeta.keyParam() != null) {
                IdMeta id = tableMetadata.getId();
                paramMap.put(id.getField(), value);
            } else if (paramMeta != null) {
                paramMap.put(paramMeta.getParamName(), value);
            }
        }
        invokeParam(tableMetadata, paramMap, null);
        if (paramMap.size() == 1) {
            return paramMap.values().iterator().next();
        }
        return paramMap;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
