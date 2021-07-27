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
    protected FromSnippet fromSnippet;
    protected ConditionSnippet conditionSnippet;
    protected InsertValueSnippet insertColumnValue;
    protected UpdateSetSnippet updateColumnSnippet;
    protected UpdateConditionSnippet updateConditionSnippet;
    protected DeleteConditionSnippet deleteConditionSnippet;
    protected DeleteSetLogicSnippet deleteLogicSnippet;
    protected OrderBySnippet orderBySnippet;
    protected PageSnippet pageSnippet;
    protected PlaceholderBuilder placeholderBuilder;


    public AbstractSqlSourceGenerator(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

    public String insertColumn(MethodMeta methodMeta) {
        return methodMeta.insertColumnList()
                .stream().map(column ->
                        placeholderBuilder.columnHolder(null, column.getColumn()).getHolder())
                .collect(Collectors.joining(", "));
    }


    public String insertColumnValue(MethodMeta methodMeta) {
        return insertColumnValue.apply(methodMeta);
    }


}
