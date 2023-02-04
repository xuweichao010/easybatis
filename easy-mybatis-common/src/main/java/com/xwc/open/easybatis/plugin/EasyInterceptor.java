package com.xwc.open.easybatis.plugin;


import com.xwc.open.easy.parse.enums.FillType;
import com.xwc.open.easy.parse.model.FillAttribute;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.TableMeta;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.fill.FillAttributeHandler;
import com.xwc.open.easybatis.fill.FillWrapper;
import com.xwc.open.easybatis.fill.MapFillWrapper;
import com.xwc.open.easybatis.fill.ObjectFillWrapper;
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

@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "parameterize", args = {Statement.class})
        }
)
public class EasyInterceptor implements Interceptor {

    private EasyBatisConfiguration easyBatisConfiguration;
    private static final String PARAM_OBJECT = "parameterHandler.parameterObject";
    private static final String MAPPED_STATEMENT = "parameterHandler.mappedStatement";


    public EasyInterceptor(EasyBatisConfiguration easyBatisConfiguration) {
        this.easyBatisConfiguration = easyBatisConfiguration;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MetaObject metaObject = SystemMetaObject.forObject(invocation.getTarget());
        MappedStatement mappedStatement = mappedStatement(metaObject);
        OperateMethodMeta operateMethodMeta = easyBatisConfiguration.getOperateMethodMeta(mappedStatement.getId());
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (operateMethodMeta == null) {
            return invocation.proceed();
        }
        Map<String, Object> map = methodParams(operateMethodMeta, metaObject);
        fill(map, operateMethodMeta, sqlCommandType);
        logic(map, operateMethodMeta, sqlCommandType);
        if (map.size() > 1) {
            metaObject.setValue(PARAM_OBJECT, map);
        }
        return invocation.proceed();
    }

    private void logic(Map<String, Object> map, OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {

    }

    /**
     * 尝试填充数据
     * 根据元数据和sql类型判断用户是否需要进行属性填充
     *
     * @param map               数据对象
     * @param operateMethodMeta 需要填充的方法
     * @param sqlCommandType    操作类型
     */
    private void fill(Map<String, Object> map, OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        List<FillAttribute> fillAttributes = null;
        TableMeta tableMeta = operateMethodMeta.getDatabaseMeta();
        if (sqlCommandType == SqlCommandType.INSERT) {
            fillAttributes = tableMeta.insertFillAttributes();
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            fillAttributes = tableMeta.updateFillAttributes();
        }
        if (fillAttributes == null || easyBatisConfiguration.getFillAttributeHandlers().isEmpty()) {
            return;
        }
        try {
            easyBatisConfiguration.getFillAttributeHandlers().forEach(FillAttributeHandler::fillBefore);
            doFill(map, operateMethodMeta, fillAttributes);
        } finally {
            easyBatisConfiguration.getFillAttributeHandlers().forEach(FillAttributeHandler::fillAfter);
        }
    }

    /**
     * 填充数据 根绝参数来决定如何进行参数填充
     *
     * @param map
     * @param operateMethodMeta
     * @param fillAttributes
     */
    private void doFill(Map<String, Object> map, OperateMethodMeta operateMethodMeta,
                        List<FillAttribute> fillAttributes) {
        // 需要区分用对象填充还是参数填充 对象填充 参数是entity对象 参数填充 就是参数列表中没有entity对象
        ParameterAttribute entityParam = operateMethodMeta.getParameterAttributes().stream()
                .filter(parameterAttribute -> parameterAttribute instanceof EntityParameterAttribute)
                .findAny().orElse(null);
        if (entityParam != null) {
            Object data = map.get(entityParam.getParameterName());
            if (data instanceof List) {
                ((List<?>) data).forEach(item -> {
                    ObjectFillWrapper objectFillWrapper = new ObjectFillWrapper(operateMethodMeta.getDatabaseMeta(), item);
                    fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, objectFillWrapper));
                });
            } else {
                ObjectFillWrapper objectFillWrapper = new ObjectFillWrapper(operateMethodMeta.getDatabaseMeta(), data);
                fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, objectFillWrapper));
            }
        } else {
            MapFillWrapper mapFillWrapper = new MapFillWrapper(map);
            fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, mapFillWrapper));
        }
    }

    /**
     * 执行参数填充
     *
     * @param fillAttribute 需要进行填充的属性
     * @param wrapper       填充对象
     */
    private void executorFill(FillAttribute fillAttribute, FillWrapper wrapper) {
        if (fillAttribute.getType() == FillType.INSERT || fillAttribute.getType() == FillType.INSERT_UPDATE) {
            easyBatisConfiguration.getFillAttributeHandlers()
                    .forEach(fillAttributeHandler -> fillAttributeHandler.insertFill(fillAttribute.getField(), wrapper));
        }
        if (fillAttribute.getType() == FillType.UPDATE || fillAttribute.getType() == FillType.INSERT_UPDATE) {
            easyBatisConfiguration.getFillAttributeHandlers()
                    .forEach(fillAttributeHandler -> fillAttributeHandler.updateFill(fillAttribute.getField(), wrapper));
        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);

    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> methodParams(OperateMethodMeta operateMethodMeta, MetaObject metaObject) {
        Object value = metaObject.getValue(PARAM_OBJECT);
        if (value instanceof Map) {
            return (Map<String, Object>) value;
        }
        HashMap<String, Object> params = new HashMap<>();
        if(!operateMethodMeta.getParameterAttributes().isEmpty()){
            ParameterAttribute parameterAttribute = operateMethodMeta.getParameterAttributes().get(0);
            params.put(parameterAttribute.getParameterName(), value);
        }
        return params;
    }

    public MappedStatement mappedStatement(MetaObject metaObject) {
        return (MappedStatement) metaObject.getValue(MAPPED_STATEMENT);
    }


}
