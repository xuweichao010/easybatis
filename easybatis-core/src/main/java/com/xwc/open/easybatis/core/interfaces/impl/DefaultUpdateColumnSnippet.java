package com.xwc.open.easybatis.core.interfaces.impl;

import com.xwc.open.easybatis.core.interfaces.UpdateColumnSnippet;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;

import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新列片段SQL构建
 */
public class DefaultUpdateColumnSnippet implements UpdateColumnSnippet {
    public String apply(MethodMeta methodMeta) {
        ParamMeta paramMeta = methodMeta.getParamMetaList().get(0);
        if (methodMeta.getParamMetaList().size() == 1 && paramMeta.isEntity()) {
            return methodMeta.updateColumnList().stream().map(this::setParam).collect(Collectors.joining(", "));
        } else {
            return null;
        }
    }

    public String setParam(ColumnMeta columnMeta) {
        return columnMeta.getColumn() + " = " + "#{" + columnMeta.getField() + "}";
    }
}
