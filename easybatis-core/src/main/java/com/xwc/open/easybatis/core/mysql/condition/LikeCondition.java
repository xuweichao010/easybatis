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
public class LikeCondition extends AbstractQueryCondition {
    {
        this.addQueryCondition(ConditionType.LIKE, "LIKE CONCAT('%%',%s,'%%')");
        this.addQueryCondition(ConditionType.RIGHT_LIKE, "LIKE CONCAT('%%',%s)");
        this.addQueryCondition(ConditionType.LEFT_LIKE, "LIKE CONCAT(%s,'%%')");
        this.addQueryCondition(ConditionType.NOT_LIKE, "NOT LIKE CONCAT('%%',%s,'%%')");
        this.addQueryCondition(ConditionType.NOT_LEFT_LIKE, "NOT LIKE CONCAT('%%',%s)");
        this.addQueryCondition(ConditionType.NOT_RIGHT_LIKE, "NOT LIKE CONCAT(%s,'%%')");
    }

    public LikeCondition(PlaceholderBuilder placeholderBuilder) {
        super(placeholderBuilder);
    }

    @Override
    public String apply(ParamMapping mapping) {
        if (!this.hasCondition(mapping.getCondition())) return null;
        String condition = builderLikeCondition(mapping);
        if (mapping.isDynamic()) {
            return dynamicCondition(mapping.getPlaceholderName().getName(), condition);
        } else {
            return nonDynamicCondition(condition);
        }
    }

    public String builderLikeCondition(ParamMapping mapping) {
        return placeholderBuilder.columnHolder(mapping.getAlias(), mapping.getColumnName()).getHolder()
                + " " + String.format(this.queryConditionExpression(mapping.getCondition()),
                mapping.getPlaceholderName().getHolder());
    }


}
