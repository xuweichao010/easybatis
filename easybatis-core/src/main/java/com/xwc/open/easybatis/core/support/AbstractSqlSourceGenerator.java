package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.support.snippet.*;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:30
 * 备注：
 */
public abstract class AbstractSqlSourceGenerator implements SqlSourceGenerator {

    protected SelectColumnSnippet selectColumnSnippet;
    protected FromSnippet fromSnippet;
    protected ConditionSnippet conditionSnippet;
    protected OrderBySnippet orderBySnippet;
    protected PageSnippet pageSnippet;
    protected PlaceholderBuilder placeholderBuilder;

    protected InsertColumnSnippet insertColumnSnippet;
    protected InsertValueSnippet insertValueSnippet;

    protected UpdateSetSnippet updateColumnSnippet;
    protected UpdateConditionSnippet updateConditionSnippet;
    protected DeleteConditionSnippet deleteConditionSnippet;
    protected DeleteSetLogicSnippet deleteLogicSnippet;


    public AbstractSqlSourceGenerator(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

}
