package com.xwc.open.easybatis.core.plugin;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
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
        if (methodMeta == null || !methodMeta.hashEnhance()) {
            return invocation.proceed();
        }
        Object easybatisValue = doIntercept(methodMeta, args[1], ms);
        args[1] = easybatisValue;
        return invocation.proceed();
    }

    private Object doIntercept(MethodMeta methodMeta, Object value, MappedStatement ms) {
        Map<String, Object> paramMap;
        if (value instanceof Map) {
            paramMap = (Map<String, Object>) value;
        } else {
            paramMap = new HashMap<>();
            ParamMapping mapping = methodMeta.getParamMetaList().stream().filter(ParamMapping::isMethodParam)
                    .findAny().orElseThrow(() -> new EasyBatisException("方法参数解析错误"));
            paramMap.put(mapping.getMethodParamName(), value);
        }
        Map<String, Object> fillMap = easybatisConfiguration.getAuditorContext().get();
        invokeParam(methodMeta, paramMap, fillMap, methodMeta.getSqlCommand());
        if (paramMap.size() == 1) {
            return paramMap.values().iterator().next();
        }
        return paramMap;
    }

    private void invokeParam(MethodMeta methodMeta, Map<String, Object> paramMap, Map<String, Object> fillMap, SqlCommandType sqlCommand) {
        ParamMapping entity = methodMeta.getParamMetaList().stream().filter(ParamMapping::isEntity).findAny().orElse(null);
        if (entity == null) {
            if (sqlCommand == SqlCommandType.UPDATE) {
                methodMeta.getTableMetadata().getAuditorList().forEach(item -> {
                    if (item.getType().command() == SqlCommandType.UPDATE) {
                        Object o = fillMap.get(item.getField());
                        paramMap.put(item.getField(), o);
                    }
                });
            }
            if (sqlCommand != SqlCommandType.INSERT) {
                LogicMapping logic = methodMeta.getTableMetadata().getLogic();
                if (logic != null) {
                    paramMap.put(logic.getField(), logic.getValid());
                    if (methodMeta.isLogicallyDelete()) {
                        paramMap.put(logic.getField() + "0", logic.getInvalid());
                    }
                }
            }
        } else {
            Object o = paramMap.get(entity.getParamName());
            if (entity.isBatch()) {
                if (o instanceof Collection) {
                    Collection<?> collection = (Collection<?>) o;
                    collection.forEach(item -> {
                        invokeObject(item, methodMeta.getTableMetadata(), sqlCommand, fillMap);
                    });
                } else {
                    throw new EasyBatisException("参数错误");
                }
            } else {
                invokeObject(o, methodMeta.getTableMetadata(), sqlCommand, fillMap);
            }
        }
    }

    public void invokeObject(Object obj, TableMeta tableMeta, SqlCommandType command, Map<String, Object> fillMap) {
        if (command == SqlCommandType.UPDATE || command == SqlCommandType.INSERT) {
            tableMeta.getAuditorList().forEach(item -> {
                try {
                    if (item.getType().command() == SqlCommandType.UPDATE) {
                        Object o = fillMap.get(item.getField());
                        item.getSetter().invoke(obj, o);
                        return;
                    }
                    if (item.getType().command() == command) {
                        Object o = fillMap.get(item.getField());
                        item.getSetter().invoke(obj, o);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                }
            });
        }
        if (command == SqlCommandType.INSERT) {
            LogicMapping logic = tableMeta.getLogic();
            if (logic != null) {
                try {
                    logic.getSetter().invoke(obj, logic.getValid());
                } catch (IllegalAccessException | InvocationTargetException e) {

                }
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
