package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.model.table.IdMapping;
import com.xwc.open.easybatis.core.mysql.condition.CompareCondition;
import com.xwc.open.easybatis.core.mysql.condition.InCondition;
import com.xwc.open.easybatis.core.mysql.condition.LikeCondition;
import com.xwc.open.easybatis.core.mysql.condition.NullCondition;
import com.xwc.open.easybatis.core.support.QueryCondition;
import com.xwc.open.easybatis.core.support.snippet.SelectConditionSnippet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public class DefaultSelectConditionSnippet implements SelectConditionSnippet {
    protected List<QueryCondition> conditionList;

    public DefaultSelectConditionSnippet() {
        this.conditionList = Stream.of(new CompareCondition(), new NullCondition(), new LikeCondition(), new InCondition()).collect(Collectors.toList());
    }

    public String apply(MethodMeta methodMeta) {
        List<ParamMapping> paramMetaList = methodMeta.getParamMetaList();
        List<ParamMapping> queryList = paramMetaList.stream().filter(paramMeta -> !paramMeta.isIgnore()).collect(Collectors.toList());
        // 处理参数为主键类型的情况
        List<ParamMapping> list = new ArrayList<>();
        boolean multi = paramMetaList.size() != queryList.size();
        if (queryList.size() == 1) {
            if (methodMeta.keyParam() != null) {
                IdMapping id = methodMeta.getTableMetadata().getId();
                list.add(ParamMapping.builder(id.getColumn(), id.getField(), ConditionType.EQUAL));
            } else if (paramMetaList.get(0).isMultiCondition()) {
                list.addAll(methodMeta.getParamMetaList().get(0).getChildList());
            } else {
                list.addAll(methodMeta.getParamMetaList());
            }
        } else if (paramMetaList.size() > 1) {
            paramMetaList.forEach(paramMeta -> {
                if (paramMeta.isMultiCondition()) {
                    list.addAll(paramMeta.getChildList());
                } else if (paramMeta.isPrimaryKey()) {
                    IdMapping id = methodMeta.getTableMetadata().getId();
                    list.add(ParamMapping.builder(id.getColumn(), id.getField(), ConditionType.EQUAL));
                } else {
                    list.add(paramMeta);
                    paramMeta.paramType();
                }
            });
            multi = true;
        }
        return listCondition(list, multi);
    }

    public String listCondition(List<ParamMapping> list, boolean multi) {
        String queryCondition = list.stream()
                .map(condition -> mapCondition(condition, multi))
                .filter(StringUtils::hasText).collect(Collectors.joining());
        if (queryCondition.startsWith(" AND")) {
            return queryCondition.substring(" AND".length());
        }
        return queryCondition;
    }


    private String mapCondition(ParamMapping metadata, boolean multi) {
        for (QueryCondition queryCondition : conditionList) {
            String condition = queryCondition.apply(metadata, multi);
            if (StringUtils.hasText(condition)) {
                return condition;
            }
        }
        return null;
    }
}
