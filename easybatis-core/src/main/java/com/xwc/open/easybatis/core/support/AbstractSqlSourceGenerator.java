package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.support.snippet.*;

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
    protected UpdateSetSnippet updateColumnSnippet;
    protected UpdateConditionSnippet updateConditionSnippet;
    protected DeleteConditionSnippet deleteConditionSnippet;
    protected DeleteSetLogicSnippet deleteLogicSnippet;
    protected OrderBySnippet orderBySnippet;
    protected PageSnippet pageSnippet;


    public AbstractSqlSourceGenerator() {
    }

    public String insertColumn(MethodMeta methodMeta) {
        return methodMeta.insertColumnList().stream().map(column -> "`" + column.getColumn() + "`")
                .collect(Collectors.joining(", "));
    }

    public String insertColumnValue(MethodMeta methodMeta) {
        return insertColumnValue.apply(methodMeta);
    }


}
