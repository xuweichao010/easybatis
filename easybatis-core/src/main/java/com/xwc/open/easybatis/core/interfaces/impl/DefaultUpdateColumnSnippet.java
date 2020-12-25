package com.xwc.open.easybatis.core.interfaces.impl;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.ParamType;
import com.xwc.open.easybatis.core.interfaces.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.interfaces.UpdateColumnSnippet;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新列片段SQL构建
 */
public class DefaultUpdateColumnSnippet implements UpdateColumnSnippet, MyBatisOrSqlTemplate {
    public String apply(MethodMeta methodMeta) {

        List<ParamMeta> setParamList = methodMeta.getParamMetaList().stream().filter(paramMeta1 -> paramMeta1.getCondition() == ConditionType.SET_PARAM).collect(Collectors.toList());
        if (setParamList.isEmpty()) {
            ParamMeta entityMate = methodMeta.getParamMetaList().get(0);
            if (methodMeta.getParamMetaList().size() == 1) {
                return methodMeta.updateColumnList().stream().map(param -> this.setParam(param, null)).collect(Collectors.joining(", "));
            } else {
                return methodMeta.updateColumnList().stream().map(param -> this.setParam(param, entityMate.getParamName())).collect(Collectors.joining(", "));
            }
        }
        return null;

    }

    public String setParam(ColumnMeta columnMeta, String prefix) {
        return columnMeta.getColumn() + " = " + this.mybatisParam(columnMeta.getField(), prefix);
    }
}
