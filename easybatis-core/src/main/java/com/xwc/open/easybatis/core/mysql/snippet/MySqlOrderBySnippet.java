package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.anno.condition.Count;
import com.xwc.open.easybatis.core.anno.condition.OrderBy;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.snippet.OrderBySnippet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 * OrderBy 会存在3个地方：方法上|参数上|属性上 在整个查询中OrderBy注解最多只能出现一次
 * 并且 @ASC @DSC 会和OrderBy冲突 如果出现冲突会抛出异常
 */
public class MySqlOrderBySnippet implements OrderBySnippet {
    protected final PlaceholderBuilder placeholderBuilder;

    public MySqlOrderBySnippet(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

    Map<ConditionType, String> orderConditionMap = new HashMap<>();

    {
        orderConditionMap.put(ConditionType.ORDER_BY_DESC, "DESC");
        orderConditionMap.put(ConditionType.ORDER_BY_ASC, "ASC");
        orderConditionMap.put(ConditionType.ORDER_BY, "");
    }

    @Override
    public String apply(MethodMeta methodMeta) {
        List<ParamMapping> orderList = methodMeta.getParamMetaList().stream().filter(item ->
                orderConditionMap.containsKey(item.getCondition())).collect(Collectors.toList());
        if (orderList.isEmpty()) return null;
        OrderBy annotation = methodMeta.chooseAnnotationType(OrderBy.class);
        if (annotation != null) {
            if (StringUtils.hasText(annotation.value())) {
                return annotation.value();
            } else if (orderList.size() > 1) {
                throw new EasyBatisException("@OrderBy 无法和 @ASC或@DESC混用");
            } else {
                throw new EasyBatisException("无效的 OrderBy 语句");
            }
        }
        return orderList.stream().map(this::builderOrderBy).collect(Collectors.joining(" "));
    }

    private String builderOrderBy(ParamMapping mapping) {
        String orderBy;
        if (mapping.getCondition() == ConditionType.ORDER_BY) {
            orderBy = mapping.getColumnName() + " " + orderConditionMap.get(mapping.getCondition());
        } else {
            orderBy = placeholderBuilder.columnHolder(mapping.getAlias(), mapping.getColumnName()).getHolder()
                    + " " + orderConditionMap.get(mapping.getCondition()) + ",";
        }
        if (mapping.isDynamic()) {
            return "<if test='" + mapping.getPlaceholderName().getName() + " != null'> " + orderBy + "</if>";
        } else {
            return orderBy;
        }
    }

}
