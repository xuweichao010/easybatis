package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.enums.FillType;
import com.xwc.open.easy.parse.model.*;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.fill.FillAttributeHandler;
import com.xwc.open.easybatis.fill.FillWrapper;
import com.xwc.open.easybatis.fill.MapFillWrapper;
import com.xwc.open.easybatis.fill.ObjectFillWrapper;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;
import java.util.Map;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/6 12:30
 */
public class DefaultParamArgsResolver implements ParamArgsResolver {

    private EasyBatisConfiguration easyBatisConfiguration;

    public DefaultParamArgsResolver(EasyBatisConfiguration easyBatisConfiguration) {
        this.easyBatisConfiguration = easyBatisConfiguration;
    }

    @Override
    public void methodParams(Map<String, Object> methodParams, OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        fill(methodParams, operateMethodMeta, sqlCommandType);
        logic(methodParams, operateMethodMeta, sqlCommandType);
    }


    /**
     * @param namedParamMap
     * @param operateMethodMeta
     * @param sqlCommandType
     */
    private void logic(Map<String, Object> namedParamMap, OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {
        LogicAttribute logic = operateMethodMeta.getDatabaseMeta().getLogic();
        if (logic == null) {
            return;
        }
        // 需要区分用对象填充还是参数填充 对象填充 参数是entity对象 参数填充 就是参数列表中没有entity对象
        ParameterAttribute entityParam = operateMethodMeta.getParameterAttributes().stream()
                .filter(parameterAttribute -> parameterAttribute instanceof EntityParameterAttribute)
                .findAny().orElse(null);
        if (entityParam != null) {
            Object data = namedParamMap.get(entityParam.getParameterName());
            if (data instanceof List) {
                ((List<?>) data).forEach(item -> {
                    new ObjectFillWrapper(logic, item).setValue(logic.getField(), logic.getValid());
                });
            } else {
                new ObjectFillWrapper(logic, data).setValue(logic.getField(), logic.getValid());
            }
        } else {
            MapFillWrapper mapFillWrapper = new MapFillWrapper(namedParamMap);
            // 过滤条件
            mapFillWrapper.setValue(logic.getField(), logic.getValid());
        }
    }

    private void doLogic(Map<String, Object> namedParamMap, OperateMethodMeta operateMethodMeta, LogicAttribute logic
            , SqlCommandType sqlCommandType) {

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
     * 填充数据 根据参数来决定如何进行参数填充
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
                    .forEach(fillAttributeHandler -> fillAttributeHandler.insertFill(fillAttribute.getIdentification(), fillAttribute.getField(),
                            wrapper));
        }
        if (fillAttribute.getType() == FillType.UPDATE || fillAttribute.getType() == FillType.INSERT_UPDATE) {
            easyBatisConfiguration.getFillAttributeHandlers()
                    .forEach(fillAttributeHandler -> fillAttributeHandler.updateFill(fillAttribute.getIdentification(), fillAttribute.getField(), wrapper));
        }
    }

}
