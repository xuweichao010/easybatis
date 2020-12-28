package com.xwc.open.easybatis.core.interfaces.snippet;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.interfaces.condition.CompareCondition;
import com.xwc.open.easybatis.core.interfaces.condition.QueryCondition;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.IdMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;

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
    protected List<QueryCondition> conditionList = new ArrayList<>();

    public DefaultSelectConditionSnippet() {
        this.conditionList = Stream.of(new CompareCondition()).collect(Collectors.toList());
    }

    public String apply(MethodMeta methodMeta) {
        List<ParamMeta> paramMetaList = methodMeta.getParamMetaList();
        // 处理参数为主键类型的情况
        List<ParamMeta> list = new ArrayList<>();
        if (paramMetaList.size() == 1) {
            if (paramMetaList.get(0).isPrimaryKey()) {
                IdMeta id = methodMeta.getTableMetadata().getId();
                list.add(ParamMeta.builderEqual(id.getColumn(), id.getField()));
            } else {
                list.addAll(methodMeta.getParamMetaList());
            }
        } else if (paramMetaList.size() > 1) {
            paramMetaList.forEach(paramMeta -> {
                if (paramMeta.getChildList() == null) {
                    list.add(paramMeta);
                } else {
                    list.addAll(paramMeta.getChildList());

                }
            });
        }
        if (methodMeta.getTableMetadata().getLogic() != null) {
            LoglicColumn logic = methodMeta.getTableMetadata().getLogic();
            list.add(ParamMeta.builderEqual(logic.getColumn(), logic.getField()));
        }
        // 处理方法上的对象参数条件
        String queryCondition = list.stream()
                .map(this::mapCondition)
                .filter(StringUtils::hasText).collect(Collectors.joining(" ")).trim();
        if (queryCondition.startsWith("AND")) {
            return queryCondition.substring("AND".length());
        }
        return queryCondition;
    }


    private String mapCondition(ParamMeta metadata) {
        for (QueryCondition queryCondition : conditionList) {
            String condition = queryCondition.apply(metadata);
            if (StringUtils.hasText(condition)) {
                return condition;
            }
        }
        return null;
    }
}
