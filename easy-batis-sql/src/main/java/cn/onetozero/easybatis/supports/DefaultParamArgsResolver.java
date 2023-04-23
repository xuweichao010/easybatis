package cn.onetozero.easybatis.supports;

import cn.onetozero.easy.parse.enums.FillType;
import cn.onetozero.easy.parse.enums.IdType;
import cn.onetozero.easy.parse.model.*;
import cn.onetozero.easy.parse.model.parameter.EntityParameterAttribute;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.fill.FillAttributeHandler;
import cn.onetozero.easybatis.fill.FillWrapper;
import cn.onetozero.easybatis.fill.MapFillWrapper;
import cn.onetozero.easybatis.fill.ObjectFillWrapper;
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
        for (ParameterAttribute virtualParameterAttribute : operateMethodMeta.getVirtualParameterAttributes()) {
            if (virtualParameterAttribute instanceof BatisColumnAttribute) {
                BatisColumnAttribute batisColumnAttribute = (BatisColumnAttribute) virtualParameterAttribute;
                MapFillWrapper mapFillWrapper = new MapFillWrapper(namedParamMap);
                // 过滤条件
                mapFillWrapper.setValue(virtualParameterAttribute.getParameterName(),
                        batisColumnAttribute.getVirtualValue());
            }
        }
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
        PrimaryKeyAttribute primaryKeyAttribute = null;

        TableMeta tableMeta = operateMethodMeta.getDatabaseMeta();
        if (sqlCommandType == SqlCommandType.INSERT) {
            primaryKeyAttribute = tableMeta.getPrimaryKey();
            fillAttributes = tableMeta.insertFillAttributes();
        } else if (sqlCommandType == SqlCommandType.UPDATE) {
            fillAttributes = tableMeta.updateFillAttributes();
        }
        if ((fillAttributes != null && !easyBatisConfiguration.getFillAttributeHandlers().isEmpty()) || primaryKeyAttribute != null) {
            try {
                easyBatisConfiguration.getFillAttributeHandlers().forEach(FillAttributeHandler::fillBefore);
                doFill(map, operateMethodMeta, fillAttributes, primaryKeyAttribute);
            } finally {
                easyBatisConfiguration.getFillAttributeHandlers().forEach(FillAttributeHandler::fillAfter);
            }
        }
        PrimaryKeyAttribute primaryKey = tableMeta.getPrimaryKey();
        //当ID的类型是数据库控制或者用户控制 直接不执行


    }

    /**
     * 填充数据 根据参数来决定如何进行参数填充
     *
     * @param map
     * @param operateMethodMeta
     * @param fillAttributes
     */
    private void doFill(Map<String, Object> map, OperateMethodMeta operateMethodMeta,
                        List<FillAttribute> fillAttributes, PrimaryKeyAttribute primaryKeyAttribute) {
        // 需要区分用对象填充还是参数填充 对象填充 参数是entity对象 参数填充 就是参数列表中没有entity对象
        ParameterAttribute entityParam = operateMethodMeta.getParameterAttributes().stream()
                .filter(parameterAttribute -> parameterAttribute instanceof EntityParameterAttribute)
                .findAny().orElse(null);
        if (entityParam != null) {
            Object data = map.get(entityParam.getParameterName());
            if (data instanceof List) {
                ((List<?>) data).forEach(item -> {
                    ObjectFillWrapper objectFillWrapper = new ObjectFillWrapper(operateMethodMeta.getDatabaseMeta(), item);
                    // 填充数据
                    fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, objectFillWrapper));
                    // 填充主键
                    executorPrimaryKeyFill(primaryKeyAttribute, item);
                });
            } else {
                ObjectFillWrapper objectFillWrapper = new ObjectFillWrapper(operateMethodMeta.getDatabaseMeta(), data);
                //填充数据
                fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, objectFillWrapper));
                // 填充主键
                executorPrimaryKeyFill(primaryKeyAttribute, data);
            }
        } else {
            MapFillWrapper mapFillWrapper = new MapFillWrapper(map);
            fillAttributes.forEach(fillAttribute -> executorFill(fillAttribute, mapFillWrapper));
        }
    }

    private void executorPrimaryKeyFill(PrimaryKeyAttribute primaryKey, Object data) {
        if (primaryKey == null) {
            return;
        }
        if (primaryKey.getIdType() != IdType.AUTO && primaryKey.getIdType() != IdType.INPUT) {
            ObjectFillWrapper objectFillWrapper = new ObjectFillWrapper(primaryKey, data);
            objectFillWrapper.setValue(primaryKey.getField(), primaryKey.getIdGenerateHandler().next(data));
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
