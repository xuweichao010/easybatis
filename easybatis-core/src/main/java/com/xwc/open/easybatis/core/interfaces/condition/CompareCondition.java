package com.xwc.open.easybatis.core.interfaces.condition;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.support.ParamMeta;


import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:10
 * 备注： 比较类型的
 */
public class CompareCondition implements QueryCondition {
    private final Set<ConditionType> conditionTypeSet = Stream.of(ConditionType.EQUAL, ConditionType.NOT_EQUEL, ConditionType.GT,
            ConditionType.GTQ, ConditionType.LT, ConditionType.LTQ).collect(Collectors.toSet());

    @Override
    public String apply(ParamMeta metaData) {
        if (!conditionTypeSet.contains(metaData.getCondition())) return null;
        String condition = "`" + metaData.getColumnName() + "` " +
                metaData.getCondition().expression() + " " +
                this.mybatisParam(metaData.getParamName(), metaData.getParentParamName());
        return doApply(metaData.getParamName(), condition, metaData.paramType());
    }

}
