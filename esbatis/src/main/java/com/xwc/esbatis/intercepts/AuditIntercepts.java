package com.xwc.esbatis.intercepts;

import com.xwc.esbatis.anno.enums.SqlOperationType;
import com.xwc.esbatis.assistant.GeneratorMapperAnnotationBuilder;
import com.xwc.esbatis.interfaces.AuditService;
import com.xwc.esbatis.meta.ColumMate;
import com.xwc.esbatis.meta.FieldType;
import com.xwc.esbatis.meta.MethodMate;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.sql.Statement;
import java.util.HashMap;
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
public class AuditIntercepts implements Interceptor {

    private static final String PARAM_OBJECT = "parameterHandler.parameterObject";
    private static final String MAPPED_STATEMENT = "parameterHandler.mappedStatement";
    private AuditService auditService;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (auditService == null) invocation.proceed();
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        MappedStatement ms = (MappedStatement) metaObject.getValue(MAPPED_STATEMENT);
        if (ms.getSqlCommandType() != SqlCommandType.INSERT && ms.getSqlCommandType() != SqlCommandType.UPDATE) {
            return invocation.proceed();
        }
        MethodMate methodMate = GeneratorMapperAnnotationBuilder.get(ms.getId());
        if (methodMate == null) return invocation.proceed();
        if (methodMate.getOperationType() == SqlOperationType.BASE_PARAM_UPDATE) return invocation.proceed();
        Object value = metaObject.getValue(PARAM_OBJECT);
        if (methodMate.getEntityMate().getAudit().isEmpty()) return invocation.proceed();
        value = auditInsert(value, methodMate, ms.getSqlCommandType());
        metaObject.setValue(PARAM_OBJECT, value);
        return invocation.proceed();
    }

    private Object auditInsert(Object value, MethodMate methodMate, SqlCommandType command) {
        if (value instanceof Map) {
            Map map = (Map) value;
            map.forEach((k, v) -> {
                if (methodMate.getOperationType() == SqlOperationType.BASE_INSERT) {
                    if (k instanceof List) {
                        if (command == SqlCommandType.INSERT) {
                            List list = (List) k;
                            list.forEach(obj -> {
                                setObject(obj, methodMate, false);
                            });
                        }
                    }
                } else if (methodMate.getOperationType() == SqlOperationType.BASE_PARAM_UPDATE) {
                    setMap(map, methodMate, true);
                }

            });
        } else {
            if (methodMate.getOperationType() == SqlOperationType.BASE_DELETE) {
                HashMap<Object, Object> map = new HashMap<>();
                if (methodMate.getArgs().size() == 1) {
                    map.put(methodMate.getArgs().get(0), value);
                    setMap(map, methodMate, true);
                    value = map;
                }
            } else {
                setObject(value, methodMate, command == SqlCommandType.UPDATE);
            }
        }
        return value;
    }

    private void setObject(Object value, MethodMate methodMate, boolean update) {
        Map<FieldType, ColumMate> audit = methodMate.getEntityMate().getAudit();
        audit.forEach((k, v) -> {
            try {
                switch (k) {
                    case CREATE_ID:
                        if (update) break;
                        v.getSetter().invoke(value, auditService.userId());
                        break;
                    case CREATE_NAME:
                        if (update) break;
                        v.getSetter().invoke(value, auditService.userName());
                        break;
                    case CREATE_TIME:
                        if (update) break;
                        v.getSetter().invoke(value, auditService.time());
                        break;
                    case UPDATE_ID:
                        v.getSetter().invoke(value, auditService.userId());
                        break;
                    case UPDATE_TIME:
                        v.getSetter().invoke(value, auditService.time());
                        break;
                    case UPDATE_NAME:
                        v.getSetter().invoke(value, auditService.userName());
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private void setMap(Map value, MethodMate methodMate, boolean update) {
        Map<FieldType, ColumMate> audit = methodMate.getEntityMate().getAudit();
        audit.forEach((k, v) -> {
            try {
                switch (k) {
                    case CREATE_ID:
                        if (update) break;
                        value.put(v.getField(), auditService.userId());
                        break;
                    case CREATE_NAME:
                        if (update) break;
                        value.put(v.getField(), auditService.userName());
                        break;
                    case CREATE_TIME:
                        if (update) break;
                        value.put(v.getField(), auditService.time());
                        break;
                    case UPDATE_ID:
                        value.put(v.getField(), auditService.userId());
                        break;
                    case UPDATE_TIME:
                        value.put(v.getField(), auditService.time());
                        break;
                    case UPDATE_NAME:
                        value.put(v.getField(), auditService.userName());
                        break;

                }
                ColumMate logic = methodMate.getEntityMate().getLogic();
                if (logic != null) {
                    value.put(logic.getField(), logic.getInvalid());
                }

            } catch (Exception e) {
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

    public AuditIntercepts(AuditService auditService) {
        this.auditService = auditService;
    }

    public AuditIntercepts() {
    }

    @Override
    public void setProperties(Properties properties) {

    }


}
