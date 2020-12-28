package com.xwc.open.easybatis.core.interfaces.snippet;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.interfaces.condition.QueryCondition;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.IdMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public class DefaultSelectConditionSnippet implements SelectConditionSnippet {
    protected List<QueryCondition> conditionList = new ArrayList<>();

    public String apply(MethodMeta methodMeta) {
        List<ParamMeta> paramMetaList = methodMeta.getParamMetaList();
        // 处理参数为主键类型的情况
        if (paramMetaList.size() == 1 && paramMetaList.get(0).isPrimaryKey()) {
            IdMeta id = methodMeta.getTableMetadata().getId();
            paramMetaList.add(ParamMeta.builderEqual(id.getColumn(), id.getField()));
        } else {
            paramMetaList.addAll(methodMeta.getParamMetaList());
        }
        if (methodMeta.getTableMetadata().getLogic() != null) {
            LoglicColumn logic = methodMeta.getTableMetadata().getLogic();
            paramMetaList.add(ParamMeta.builderEqual(logic.getColumn(), logic.getField()));
        }
        // 处理方法上的对象参数条件
        String queryCondition = paramMetaList.stream()
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
