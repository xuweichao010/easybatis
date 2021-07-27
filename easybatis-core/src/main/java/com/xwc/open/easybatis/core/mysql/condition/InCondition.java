package com.xwc.open.easybatis.core.mysql.condition;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.impl.AbstractQueryCondition;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:10
 * 备注：
 */
public class InCondition extends AbstractQueryCondition {
    {
        this.addQueryCondition(ConditionType.IN, "IN");
        this.addQueryCondition(ConditionType.NOT_IN, "NOT IN");
    }

    public InCondition(PlaceholderBuilder placeholderBuilder) {
        super(placeholderBuilder);
    }

    @Override
    public String apply(ParamMapping mapping) {
        if (!this.hasCondition(mapping.getCondition())) return null;
        String condition = builderInCondition(mapping);
        if (mapping.isDynamic()) {
            return dynamicCondition(mapping.getPlaceholderName().getParamPath(), condition);
        } else {
            return nonDynamicCondition(condition);
        }
    }

    private String builderInCondition(ParamMapping mapping) {
        return placeholderBuilder.columnHolder(mapping.getAlias(), mapping.getColumnName()).getHolder()
                + " " + this.queryConditionExpression(mapping.getCondition())
                + " <foreach item= 'item'  collection='" + mapping.getPlaceholderName().getParamPath()
                + "' open='(' separator=', ' close=')'>#{item}</foreach>";
    }

}
