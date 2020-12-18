package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.interfaces.condition.CompareCondition;
import com.xwc.open.easybatis.core.interfaces.condition.NullCondition;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;


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
        if (metadata.hashAnnotation(PrimaryKey.class)) {
            return mapCondition(,false);
        }
        StringBuilder sb = new StringBuilder();
        // 处理方法上的非对象参数条件
        sb.append(metadata.getParamMetaList().stream().filter(paramMetaData -> !paramMetaData.isCustom())
                .map(paramMetaData -> mapCondition(paramMetaData, metadata.isDynamic()))
                .filter(StringUtils::hasText).collect(Collectors.joining(" AND "))
        );
        // 处理方法上的对象参数条件
        sb.append(metadata.getParamMetaList().stream()
                .filter(ParamMeta::isCustom)
                .map(paramMetaData -> mapCondition(paramMetaData, metadata.isDynamic()))
                .filter(StringUtils::hasText).collect(Collectors.joining(" ")));
        // 处理逻辑删除

        return sb.toString();

    }

    private String mapCondition(ParamMeta metadata, boolean isDynamic) {
        for (QueryCondition queryCondition : list) {
            String condition = queryCondition.apply(metadata, isDynamic);
            if (StringUtils.hasText(condition)) {
                return condition;
            }
        }
        return null;
    }


}
