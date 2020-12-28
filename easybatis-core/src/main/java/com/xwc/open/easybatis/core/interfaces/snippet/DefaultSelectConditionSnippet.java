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

        return null;
    }

    public String queryCondition(MethodMeta metadata) {
        List<ParamMeta> paramMetaList = new ArrayList<>();
        if (metadata.hashAnnotationType(PrimaryKey.class)) {
            IdMeta id = metadata.getTableMetadata().getId();
            paramMetaList.add(ParamMeta.builderEqual(id.getColumn(), id.getField()));
        } else {
            paramMetaList.addAll(metadata.getParamMetaList());
        }
        if (metadata.getTableMetadata().getLogic() != null) {
            LoglicColumn logic = metadata.getTableMetadata().getLogic();
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
