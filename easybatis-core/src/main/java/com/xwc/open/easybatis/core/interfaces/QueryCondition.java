package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.enums.ParamType;
import com.xwc.open.easybatis.core.support.ParamMeta;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:07
 * 备注：
 */
public interface QueryCondition {

    String apply(ParamMeta metaData);


    default String dynamicIf(String conditionParam, String conditionQuery) {
        return "<if test=\"" + conditionParam + "\"> " + " AND " + conditionQuery + " </if>";
    }

    default String dynamicIsNull(String conditionParam, String conditionQuery) {
        return "AND ( #{" + conditionParam + "} IS NULL OR " + conditionQuery + " )";
    }

    default String doApply(String conditionParam, String conditionQuery, ParamType type) {
        if (type == ParamType.FILED_TYPE || type == ParamType.PARAM_TYPE) {
            return conditionQuery;
        } else if (type == ParamType.FILED_TYPE_DYNAMIC) {
            return dynamicIf(conditionParam, conditionQuery);
        } else if (type == ParamType.PARAM_TYPE_DYNAMIC) {
            return dynamicIsNull(conditionParam, conditionQuery);
        } else {
            return null;
        }
    }
}
