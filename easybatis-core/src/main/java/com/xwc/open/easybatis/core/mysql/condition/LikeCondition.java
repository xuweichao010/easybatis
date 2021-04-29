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
public class LikeCondition implements QueryCondition {
    private static final Set<ConditionType> conditionTypeSet = Stream.of(
            ConditionType.LIKE, ConditionType.RIGHT_LIKE,
            ConditionType.LEFT_LIKE, ConditionType.NOT_LIKE,
            ConditionType.NOT_LEFT_LIKE, ConditionType.NOT_RIGHT_LIKE
    ).collect(Collectors.toSet());

    @Override
    public String apply(ParamMapping metaData, boolean multi) {
        if (!conditionTypeSet.contains(metaData.getCondition())) return null;
        String prefix = multi && metaData.hasParent() ? metaData.getParentParamName() : null;
        String columnName = this.columnName(metaData);
        return doApply(this.paramName(metaData.getParamName(), prefix), condition(this.mybatisParam(metaData.getParamName(), prefix), columnName, metaData.getCondition()), metaData.paramType());
    }

    public String condition(String paramName, String columnName, ConditionType conditionType) {
        return columnName + " " + String.format(conditionType.expression(), paramName);
    }


}
