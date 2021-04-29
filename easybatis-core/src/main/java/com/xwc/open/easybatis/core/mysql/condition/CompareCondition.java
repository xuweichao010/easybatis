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
 * 备注： 比较类型的
 */
public class CompareCondition implements QueryCondition {
    private final Set<ConditionType> conditionTypeSet = Stream.of(ConditionType.EQUAL, ConditionType.NOT_EQUAL, ConditionType.GT,
            ConditionType.GTQ, ConditionType.LT, ConditionType.LTQ).collect(Collectors.toSet());

    @Override
    public String apply(ParamMapping metaData, boolean multi) {
        if (!conditionTypeSet.contains(metaData.getCondition())) return null;
        String prefix = multi && metaData.hasParent() ? metaData.getParentParamName() : null;
        String condition = this.columnName(metaData) + " " + metaData.getCondition().expression() + " " + this.mybatisParam(metaData.getParamName(), prefix);
        return doApply(paramName(metaData.getParamName(), prefix), condition, metaData.paramType());
    }
}
