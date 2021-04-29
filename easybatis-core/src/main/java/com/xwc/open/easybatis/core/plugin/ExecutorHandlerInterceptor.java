package com.xwc.open.easybatis.core.plugin;

import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.enums.AuditorType;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.core.model.table.AuditorMapping;
import com.xwc.open.easybatis.core.model.table.IdMapping;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import com.xwc.open.easybatis.core.support.AuditorContext;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
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
        if (methodMeta == null || !methodMeta.hashEnhance()) {
            return invocation.proceed();
        }
        Object easybatisValue = doIntercept(methodMeta, args[1], ms);
        args[1] = easybatisValue;
        easybatisConfiguration.getAuditorContext().clear();
        return invocation.proceed();
    }

    public Object doIntercept(MethodMeta methodMeta, Object value, MappedStatement ms) {
        if (methodMeta.getSqlCommand() == SqlCommandType.SELECT) {
            return doInterceptSelect(methodMeta, value, ms);
        } else if (methodMeta.getSqlCommand() == SqlCommandType.INSERT) {
            return doInterceptInsert(methodMeta, value);
        } else if (methodMeta.getSqlCommand() == SqlCommandType.UPDATE) {
            return doInterceptUpdate(methodMeta, value);
        } else if (methodMeta.getSqlCommand() == SqlCommandType.DELETE) {
            return doInterceptDelete(methodMeta, value);
        }
        return value;
    }

    private Object doInterceptDelete(MethodMeta methodMeta, Object value) {
        Map<String, Object> paramMap;
        TableMeta tableMetadata = methodMeta.getTableMetadata();
        if (value instanceof Map) {
            paramMap = (Map<String, Object>) value;
        } else {
            paramMap = new HashMap<>();
            ParamMapping paramMeta = methodMeta.singleParam();
            if (methodMeta.keyParam() != null) {
                IdMapping id = tableMetadata.getId();
                paramMap.put(id.getField(), value);
            } else if (paramMeta != null) {
                paramMap.put(paramMeta.getParamName(), value);
            }
        }
        invokeParam(tableMetadata, paramMap, methodMeta.getSqlCommand());
        if (paramMap.size() == 1) {
            return paramMap.values().iterator().next();
        }
        return paramMap;
    }

    @SuppressWarnings("all")
    private Object doInterceptSelect(MethodMeta methodMeta, Object value, MappedStatement ms) {
        Map<String, Object> paramMap;
        TableMeta tableMetadata = methodMeta.getTableMetadata();
        if (value instanceof Map) {
            paramMap = (Map<String, Object>) value;
        } else {
            paramMap = new HashMap<>();
            ParamMapping paramMeta = methodMeta.singleParam();
            if (methodMeta.keyParam() != null) {
                IdMapping id = tableMetadata.getId();
                paramMap.put(id.getField(), value);
            } else if (paramMeta != null) {
                paramMap.put(paramMeta.getParamName(), value);
            }
        }
        invokeParam(tableMetadata, paramMap, methodMeta.getSqlCommand());
        if (paramMap.size() == 1) {
            return paramMap.values().iterator().next();
        }
        return paramMap;
    }

    private Object doInterceptUpdate(MethodMeta methodMeta, Object value) {
        Map<String, Object> paramMap;
        TableMeta tableMetadata = methodMeta.getTableMetadata();
        if (value instanceof Map) {
            paramMap = (Map<String, Object>) value;
        } else {
            paramMap = new HashMap<>();
            ParamMapping paramMeta = methodMeta.singleParam();
            if (methodMeta.keyParam() != null) {
                IdMapping id = tableMetadata.getId();
                paramMap.put(id.getField(), value);
            } else {
                paramMap.put(paramMeta.getParamName(), value);
            }
        }
        if (methodMeta.entityParam() != null) {
            paramMap.values().forEach(item -> {
                this.invokeObject(item, tableMetadata, methodMeta.getSqlCommand());
            });
        } else {
            this.invokeParam(tableMetadata, paramMap, methodMeta.getSqlCommand());
        }
        if (paramMap.size() == 1) {
            return paramMap.values().iterator().next();
        }
        return paramMap;
    }

    private Object doInterceptInsert(MethodMeta methodMeta, Object value) {
        TableMeta tableMetadata = methodMeta.getTableMetadata();
        if (value instanceof List) {
            ((List<?>) value).forEach(item -> {
                invokeObject(item, tableMetadata, methodMeta.getSqlCommand());
            });
        } else if (value instanceof Map) {
            Map<String, Object> map = (Map) value;
            map.forEach((key, mapValue) -> {
                if (key.startsWith("param")) {
                    return;
                }
                if (mapValue instanceof List) {
                    ((List<?>) mapValue).forEach(item -> {
                        invokeObject(item, tableMetadata, methodMeta.getSqlCommand());
                    });
                } else {
                    invokeObject(mapValue, tableMetadata, methodMeta.getSqlCommand());
                }
            });
        } else {
            invokeObject(value, tableMetadata, methodMeta.getSqlCommand());
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

    private void invokeObject(Object node, TableMeta tableMetadata, SqlCommandType command) {
        AuditorContext auditorContext = easybatisConfiguration.getAuditorContext();
        SqlCommandType realCommand = command;
        if (tableMetadata.isSource(node.getClass())) {
            LogicMapping logic = tableMetadata.getLogic();
            if (logic != null) {
                invokeMethod(node, logic.getSetter(), logic.getValid());
                if (realCommand == SqlCommandType.DELETE) {
                    realCommand = SqlCommandType.UPDATE;
                }
            }
            if (!tableMetadata.getAuditorMap().isEmpty() && (realCommand == SqlCommandType.INSERT
                    || realCommand == SqlCommandType.UPDATE)) {
                for (AuditorMapping item : tableMetadata.getAuditorMap().values()) {
                    if (SqlCommandType.INSERT == realCommand) {
                        if (item.getType() == AuditorType.CREATE_ID) {
                            invokeMethod(node, item.getSetter(), auditorContext.id());
                        } else if (item.getType() == AuditorType.CREATE_NAME) {
                            invokeMethod(node, item.getSetter(), auditorContext.name());
                        } else if (item.getType() == AuditorType.CREATE_TIME) {
                            invokeMethod(node, item.getSetter(), auditorContext.time());
                        }
                    }
                    if (item.getType() == AuditorType.UPDATE_ID) {
                        invokeMethod(node, item.getSetter(), auditorContext.id());
                    } else if (item.getType() == AuditorType.UPDATE_NAME) {
                        invokeMethod(node, item.getSetter(), auditorContext.name());
                    } else if (item.getType() == AuditorType.UPDATE_TIME) {
                        invokeMethod(node, item.getSetter(), auditorContext.time());
                    }
                }
            }
        }
    }

    private void invokeParam(TableMeta tableMetadata, Map<String, Object> paramMap, SqlCommandType command) {
        AuditorContext auditorContext = easybatisConfiguration.getAuditorContext();
        LogicMapping logic = tableMetadata.getLogic();
        SqlCommandType realCommand = command;
        if (logic != null) {
            paramMap.put(logic.getField(), logic.getValid());
            if (realCommand == SqlCommandType.DELETE) {
                paramMap.put("invalid", logic.getInvalid());
            }
        }
        if (!tableMetadata.getAuditorMap().isEmpty() && (realCommand == SqlCommandType.INSERT
                || realCommand == SqlCommandType.UPDATE)) {
            for (AuditorMapping item : tableMetadata.getAuditorMap().values()) {
                if (SqlCommandType.INSERT == realCommand) {
                    if (item.getType() == AuditorType.CREATE_ID) {
                        paramMap.put(item.getField(), auditorContext.id());
                    } else if (item.getType() == AuditorType.CREATE_NAME) {
                        paramMap.put(item.getField(), auditorContext.name());
                    } else if (item.getType() == AuditorType.CREATE_TIME) {
                        paramMap.put(item.getField(), auditorContext.time());
                    }
                }
                if (item.getType() == AuditorType.UPDATE_ID) {
                    paramMap.put(item.getField(), auditorContext.id());
                } else if (item.getType() == AuditorType.UPDATE_NAME) {
                    paramMap.put(item.getField(), auditorContext.name());
                } else if (item.getType() == AuditorType.UPDATE_TIME) {
                    paramMap.put(item.getField(), auditorContext.time());
                }
            }
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

}
