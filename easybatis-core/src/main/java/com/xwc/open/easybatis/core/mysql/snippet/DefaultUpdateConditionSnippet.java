package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMeta;
import com.xwc.open.easybatis.core.model.table.IdMeta;
import com.xwc.open.easybatis.core.model.table.LoglicColumn;
import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.support.snippet.SelectConditionSnippet;
import com.xwc.open.easybatis.core.support.snippet.UpdateConditionSnippet;

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
            StringBuilder sb = new StringBuilder();
            ParamMeta paramMeta = null;
            if (methodMeta.getParamMetaList().size() > 1) {
                paramMeta = methodMeta.getParamMetaList().stream().filter(ParamMeta::isEntity).collect(Collectors.toList()).get(0);
            }
            IdMeta id = methodMeta.getTableMetadata().getId();
            sb.append(" `" + id.getColumn() + "` = " + this.mybatisParam(id.getField(), paramMeta == null ? null : paramMeta.getParamName()));
            LoglicColumn logic = methodMeta.getTableMetadata().getLogic();
            if (logic != null) {
                sb.append(" AND `" + logic.getColumn() + "` = " + this.mybatisParam(logic.getField(), null));
            }
            return sb.toString();
        } else {
            return selectConditionSnippet.apply(methodMeta);
        }
    }
}
