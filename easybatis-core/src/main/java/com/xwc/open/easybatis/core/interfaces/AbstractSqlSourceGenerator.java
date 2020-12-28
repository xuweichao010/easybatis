package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.interfaces.condition.QueryCondition;
import com.xwc.open.easybatis.core.interfaces.snippet.*;
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

    protected SelectColumnSnippet selectColumnSnippet;
    protected SelectConditionSnippet selectConditionSnippet;
    protected InsertValueSnippet insertColumnValue;
    protected UpdateColumnSnippet updateColumnSnippet;
    protected UpdateConditionSnippet updateConditionSnippet;

    public AbstractSqlSourceGenerator() {
        this.selectColumnSnippet = new DefaultSelectColumnSnippet();
        this.selectConditionSnippet = new DefaultSelectConditionSnippet();
    }

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




}
