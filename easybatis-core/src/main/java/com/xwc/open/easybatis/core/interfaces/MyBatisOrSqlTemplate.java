package com.xwc.open.easybatis.core.interfaces;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：
 */
public interface MyBatisOrSqlTemplate {

    default String dynamicConditionIf(String paramName, String conditionQuery) {
        return "<if test='" + paramName + "'> " + " AND " + conditionQuery + " </if>";
    }

    default String dynamicSetIf(String setParam, String setValue) {
        return "<if test='" + setParam + "'> " + setValue + " </if>";
    }

    default String dynamicConditionIsNull(String paramName, String conditionQuery) {
        return "AND (" + mybatisParam(paramName) + " IS NULL OR " + conditionQuery + ")";
    }

    default String insertBatchForeach(String fieldList, String paramName) {
        if (paramName == null) {
            paramName = "list";
        }
        return " <foreach item= 'item'  collection='" + paramName + "' separator=', '> "
                + fieldList
                + " </foreach>";
    }

    default String mybatisParam(String fieldName, String... prefix) {
        if (prefix.length == 1) {
            return "#{ " + prefix[0] + "." + fieldName + "}";
        }
        return "#{" + fieldName + "}";
    }
}
