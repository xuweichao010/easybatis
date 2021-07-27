package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.mysql.condition.CompareCondition;
import com.xwc.open.easybatis.core.mysql.condition.InCondition;
import com.xwc.open.easybatis.core.mysql.condition.LikeCondition;
import com.xwc.open.easybatis.core.mysql.condition.NullCondition;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.QueryCondition;
import com.xwc.open.easybatis.core.support.snippet.ConditionSnippet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public class MySqlConditionSnippet implements ConditionSnippet {
    protected List<QueryCondition> conditionList;

    private PlaceholderBuilder placeholderBuilder;

    public MySqlConditionSnippet(PlaceholderBuilder placeholderBuilder) {
        this.conditionList = Stream.of(new CompareCondition(placeholderBuilder), new NullCondition(placeholderBuilder),
                new LikeCondition(placeholderBuilder), new InCondition(placeholderBuilder)).collect(Collectors.toList());
    }

    public String apply(MethodMeta methodMeta) {
        return listCondition(methodMeta.getParamMetaList());
    }

    public String listCondition(List<ParamMapping> list) {
        String queryCondition = list.stream()
                .map(this::mapCondition)
                .filter(StringUtils::hasText).collect(Collectors.joining());
        if (queryCondition.startsWith(" AND")) {
            return queryCondition.substring(" AND".length());
        }
        return queryCondition;
    }


    private String mapCondition(ParamMapping metadata) {
        for (QueryCondition queryCondition : conditionList) {
            String condition = queryCondition.apply(metadata);
            if (StringUtils.hasText(condition)) {
                return condition;
            }
        }
        return null;
    }
}
