package com.xwc.open.easybatis.core.mysql.condition;

import com.xwc.open.easybatis.core.commons.StringUtils;
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
public class NullCondition implements QueryCondition {
    private final Set<ConditionType> conditionTypeSet = Stream.of(ConditionType.IS_NULL, ConditionType.NOT_NULL).collect(Collectors.toSet());

    @Override
    public String apply(ParamMapping metaData, boolean multi) {
        if (!conditionTypeSet.contains(metaData.getCondition())) return null;
        String condition = "`" + metaData.getColumnName() + "` " +
                metaData.getCondition().expression();
        if (StringUtils.hasText(metaData.getAlias())) {
            condition = metaData.getAlias() + "." + condition;
        }
        return doApply(metaData.getParamName(), condition, metaData.paramType());
    }
}
