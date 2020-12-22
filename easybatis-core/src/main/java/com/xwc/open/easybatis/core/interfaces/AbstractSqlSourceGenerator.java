package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;
import com.xwc.open.easybatis.core.support.table.PrimaryKey;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:30
 * 备注：
 */
public abstract class AbstractSqlSourceGenerator implements SqlSourceGenerator {
    protected List<QueryCondition> list = new ArrayList<>();


    public String selectColumn(MethodMeta metadata) {
        SelectSql selectSql = metadata.findAnnotation(SelectSql.class);
        if (StringUtils.hasText(selectSql.value())) {
            return selectSql.value();
        }
        ArrayList<ColumnMeta> list = new ArrayList<>();
        list.addAll(metadata.getTableMetadata().getColumnMetaList());
        list.addAll(metadata.getTableMetadata().getAuditorList());
        return list.stream()
                .filter(column -> !column.isSelectIgnore())
                .map(column -> "`" + column.getColumn() + "`").collect(Collectors.joining(","));

    }

    public String queryCondition(MethodMeta metadata) {
        List<ParamMeta> paramMetaList = new ArrayList<>();
        if (metadata.hashAnnotation(com.xwc.open.easybatis.core.anno.condition.PrimaryKey.class)) {
            PrimaryKey id = metadata.getTableMetadata().getId();
            paramMetaList.add(ParamMeta.builder(id.getColumn(), id.getField(), ConditionType.EQUEL));
        } else {
            paramMetaList.addAll(metadata.getParamMetaList());
        }
        if (metadata.getTableMetadata().getLogic() != null) {
            LoglicColumn logic = metadata.getTableMetadata().getLogic();
            paramMetaList.add(ParamMeta.builder(logic.getColumn(), logic.getField(), ConditionType.EQUEL));
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
        for (QueryCondition queryCondition : list) {
            String condition = queryCondition.apply(metadata);
            if (StringUtils.hasText(condition)) {
                return condition;
            }
        }
        return null;
    }


}
