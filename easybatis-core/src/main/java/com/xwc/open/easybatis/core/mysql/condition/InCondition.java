package com.xwc.open.easybatis.core.mysql.condition;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.support.QueryCondition;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:10
 * 备注：
 */
public class InCondition implements QueryCondition {
    private final Set<ConditionType> conditionTypeSet = Stream.of(ConditionType.IN, ConditionType.NOT_IN).collect(Collectors.toSet());

    @Override
    public String apply(ParamMapping metaData, boolean multi) {
        if (!conditionTypeSet.contains(metaData.getCondition())) return null;
        String condition;
        String paramName;
        if (multi) {
            paramName = this.paramName(metaData.getParamName(), metaData.hasParent() ? metaData.getParentParamName() : null);
            condition = metaData.getColumnName() + " " + metaData.getCondition().expression() + this.inForeach(paramName);
        } else {
            condition = metaData.getColumnName() + " " + metaData.getCondition().expression() + this.inForeach(null);
            paramName = metaData.getParamName();
        }
        return doApply(paramName, condition, metaData.paramType());
    }
}
