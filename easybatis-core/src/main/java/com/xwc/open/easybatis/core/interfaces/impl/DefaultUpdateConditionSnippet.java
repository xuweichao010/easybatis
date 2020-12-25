package com.xwc.open.easybatis.core.interfaces.impl;

import com.xwc.open.easybatis.core.interfaces.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.interfaces.UpdateConditionSnippet;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.IdMeta;

import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：
 */
public class DefaultUpdateConditionSnippet implements UpdateConditionSnippet, MyBatisOrSqlTemplate {
    @Override
    public String apply(MethodMeta methodMeta) {
        ParamMeta paramMeta = methodMeta.getParamMetaList().get(0);
        if (methodMeta.getParamMetaList().size() == 1 && paramMeta.isEntity()) {
            IdMeta id = methodMeta.getTableMetadata().getId();
            return id.getColumn() + " = " + this.mybatisParam(id.getField());
        } else {
            return null;
        }
    }
}
