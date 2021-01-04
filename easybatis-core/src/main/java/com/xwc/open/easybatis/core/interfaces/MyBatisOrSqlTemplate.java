package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.support.ParamMeta;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：
 */
public interface MyBatisOrSqlTemplate {

    default String dynamicConditionIf(String paramName, String conditionQuery) {
        return "<if test='" + paramName + " != null'> " + andCondition(conditionQuery) + " </if>";
    }

    default String andCondition(String conditionQuery) {
        return " AND " + conditionQuery;
    }


    default String dynamicSetIf(String setParam, String setValue) {
        return "<if test='" + setParam + " != null'> " + setValue + ",</if>";
    }

    default String dynamicConditionIsNull(String paramName, String conditionQuery) {
        return "AND (" + mybatisParam(paramName, null) + " IS NULL OR " + conditionQuery + ")";
    }

    default String insertBatchForeach(String fieldList, String paramName) {
        if (paramName == null) {
            paramName = "list";
        }
        return " <foreach item= 'item'  collection='" + paramName + "' separator=', '> "
                + fieldList
                + " </foreach>";
    }

    default String inForeach(String paramName) {
        if (paramName == null) {
            paramName = "list";
        }
        return " <foreach item= 'item'  collection='" + paramName + "' separator=', ' open='(' separator=',' close=')'>#{item}</foreach>";
    }


    default String mybatisParam(String fieldName, String prefix) {
        return "#{" + paramName(fieldName, prefix) + "}";
    }

    default String paramName(String fieldName, String prefix) {
        return prefix != null ? prefix + "." + fieldName : fieldName;
    }

    default String columnName(ParamMeta paramMeta, boolean multi) {
        if (StringUtils.hasText(paramMeta.getAlias())) {
            return paramMeta.getAlias() + ".`" + paramMeta.getColumnName() + "`";
        }
        return "`" + paramMeta.getColumnName() + "`";
    }
}
