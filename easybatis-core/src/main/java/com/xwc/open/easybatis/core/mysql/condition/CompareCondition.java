package com.xwc.open.easybatis.core.mysql.condition;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.impl.AbstractQueryCondition;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:10
 * 备注： 比较类型的
 */
public class CompareCondition extends AbstractQueryCondition {

    {
        this.addQueryCondition(ConditionType.EQUAL, "=");
        this.addQueryCondition(ConditionType.NOT_EQUAL, "!=");
        this.addQueryCondition(ConditionType.GT, "<![CDATA[>]]>");
        this.addQueryCondition(ConditionType.LT, "<![CDATA[<]]>");
        this.addQueryCondition(ConditionType.GTQ, "<![CDATA[>=]]>");
        this.addQueryCondition(ConditionType.LTQ, "<![CDATA[<=]]>");
    }

    public CompareCondition(PlaceholderBuilder placeholderBuilder) {
        super(placeholderBuilder);
    }

    @Override
    public String apply(ParamMapping mapping) {
        if (!this.hasCondition(mapping.getCondition())) return null;
        String condition = builderCompareCondition(mapping);
        if (mapping.isDynamic()) {
            return dynamicCondition(mapping.getPlaceholderName().getParamPath(), condition);
        } else {
            //return nonDynamicCondition(condition);
            return condition;
        }
    }

    public String builderCompareCondition(ParamMapping mapping) {
        return " AND " + placeholderBuilder.columnHolder(mapping.getAlias(), mapping.getColumnName()).getHolder()
                + " " + this.queryConditionExpression(mapping.getCondition()) + " "
                + mapping.getPlaceholderName().getHolder();
    }
}
