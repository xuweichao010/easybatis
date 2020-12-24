package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.interfaces.impl.DefaultInsertValueField;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;
import com.xwc.open.easybatis.core.support.table.IdMeta;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:30
 * 备注：
 */
public abstract class AbstractSqlSourceGenerator implements SqlSourceGenerator {
    protected List<QueryCondition> conditionList = new ArrayList<>();
    protected InsertValueField insertColumnValue;


    public String selectColumn(MethodMeta metadata) {
        SelectSql selectSql = metadata.chooseAnnotationType(SelectSql.class);
        if (StringUtils.hasText(selectSql.value())) {
            return selectSql.value();
        }
        return metadata.selectColumnList().stream()
                .map(column -> "`" + column.getColumn() + "`").collect(Collectors.joining(", "));

    }

    public String insertColumn(MethodMeta methodMeta) {
        return methodMeta.insertColumnList().stream().map(column -> "`" + column.getColumn() + "`")
                .collect(Collectors.joining(", "));
    }

    public String insertColumnValue(MethodMeta methodMeta) {
        return insertColumnValue.apply(methodMeta);
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
