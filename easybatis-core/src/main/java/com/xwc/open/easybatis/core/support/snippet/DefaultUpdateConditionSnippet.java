package com.xwc.open.easybatis.core.support.snippet;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMeta;
import com.xwc.open.easybatis.core.model.table.IdMeta;
import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：
 */
public class DefaultUpdateConditionSnippet implements UpdateConditionSnippet, MyBatisOrSqlTemplate {
    private SelectConditionSnippet selectConditionSnippet;

    public DefaultUpdateConditionSnippet(SelectConditionSnippet selectConditionSnippet) {
        this.selectConditionSnippet = selectConditionSnippet;
    }

    @Override
    public String apply(MethodMeta methodMeta) {
        List<ParamMeta> paramList = methodMeta.getParamMetaList();
        List<ParamMeta> setParamList = paramList.stream()
                .filter(paramMeta1 -> paramMeta1.getCondition() == ConditionType.SET_PARAM).collect(Collectors.toList());
        if (setParamList.isEmpty()) {
            ParamMeta paramMeta = null;
            if (methodMeta.getParamMetaList().size() > 1) {
                paramMeta = methodMeta.getParamMetaList().stream().filter(ParamMeta::isEntity).collect(Collectors.toList()).get(0);
            }
            IdMeta id = methodMeta.getTableMetadata().getId();
            return "`"+id.getColumn() + "` = " + this.mybatisParam(id.getField(), paramMeta == null ? null : paramMeta.getParamName());
        } else {
            return selectConditionSnippet.apply(methodMeta);
        }
    }
}
