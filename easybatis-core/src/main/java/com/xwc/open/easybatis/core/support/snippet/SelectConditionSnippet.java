package com.xwc.open.easybatis.core.support.snippet;

import com.xwc.open.easybatis.core.model.MethodMeta;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public interface SelectConditionSnippet {
    String apply(MethodMeta methodMeta);
}
