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
public class NullCondition extends AbstractQueryCondition {
    {
        this.addQueryCondition(ConditionType.IS_NULL, "IS NULL");
        this.addQueryCondition(ConditionType.NOT_NULL, "IS NOT NULL");
    }

    public NullCondition(PlaceholderBuilder placeholderBuilder) {
        super(placeholderBuilder);
    }

    @Override
    public String apply(ParamMapping mapping) {
        if (!this.hasCondition(mapping.getCondition())) return null;
        String condition = builderNullCondition(mapping);
        if (mapping.isDynamic()) {
            return dynamicCondition(mapping.getPlaceholderName().getName(), condition);
        } else {
            return condition;
        }
    }

    private String builderNullCondition(ParamMapping mapping) {
        return " AND " + placeholderBuilder.columnHolder(mapping.getAlias(), mapping.getColumnName()).getHolder() + " " +
                this.queryConditionExpression(mapping.getCondition());
    }
}
