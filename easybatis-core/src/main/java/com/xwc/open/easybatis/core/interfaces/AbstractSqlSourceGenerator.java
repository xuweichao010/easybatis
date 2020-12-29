package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.interfaces.snippet.*;
import com.xwc.open.easybatis.core.support.MethodMeta;

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
    protected DeleteConditionSnippet deleteConditionSnippet;

    public AbstractSqlSourceGenerator() {
        this.selectColumnSnippet = new DefaultSelectColumnSnippet();
        this.selectConditionSnippet = new DefaultSelectConditionSnippet();
        this.deleteConditionSnippet = new DefaultDeleteConditionSnippet(selectConditionSnippet);
        this.insertColumnValue = new DefaultInsertValueSnippet();
        this.updateColumnSnippet = new DefaultUpdateColumnSnippet();
        this.updateConditionSnippet = new DefaultUpdateConditionSnippet(this.selectConditionSnippet);
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
