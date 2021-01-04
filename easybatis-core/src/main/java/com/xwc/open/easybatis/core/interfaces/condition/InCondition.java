package com.xwc.open.easybatis.core.interfaces.condition;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.support.ParamMeta;

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
    public String apply(ParamMeta metaData, boolean multi) {
        return null;
    }
}
