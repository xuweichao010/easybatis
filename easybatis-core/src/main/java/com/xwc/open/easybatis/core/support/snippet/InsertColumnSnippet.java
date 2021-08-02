package com.xwc.open.easybatis.core.support.snippet;

import com.xwc.open.easybatis.core.model.MethodMeta;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：字段sql构建语句
 */
public interface InsertColumnSnippet {
    String apply(MethodMeta methodMeta);
}
