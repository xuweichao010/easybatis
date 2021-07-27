package com.xwc.open.easybatis.core.support.impl;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.QueryCondition;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:07
 * 备注：
 */
public abstract class AbstractQueryCondition implements QueryCondition {
    private final Map<ConditionType, String> conditionExpressionMap = new HashMap<>();
    protected final PlaceholderBuilder placeholderBuilder;

    public AbstractQueryCondition(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

    public String dynamicCondition(String param, String condition) {
        return " <if test='" + param + " != null'>" + condition + " </if>";
    }

    public String nonDynamicCondition(String condition) {
        return " <if test='true'>" + condition + " </if>";
    }

    public void addQueryCondition(ConditionType conditionType, String expression) {
        conditionExpressionMap.put(conditionType, expression);
    }

    public String queryConditionExpression(ConditionType conditionType) {
        return conditionExpressionMap.get(conditionType);
    }

    public boolean hasCondition(ConditionType conditionType) {
        return conditionExpressionMap.containsKey(conditionType);
    }


}
