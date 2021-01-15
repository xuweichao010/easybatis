package com.xwc.open.easybatis.core.support.snippet;

import com.xwc.open.easybatis.core.model.MethodMeta;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public class DefaultDeleteConditionSnippet implements DeleteConditionSnippet {
    public SelectConditionSnippet selectConditionSnippet;

    public DefaultDeleteConditionSnippet(SelectConditionSnippet selectConditionSnippet) {
        this.selectConditionSnippet = selectConditionSnippet;
    }

    public String apply(MethodMeta methodMeta) {
       return this.selectConditionSnippet.apply(methodMeta);
    }
}
