package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.support.ParamMeta;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:07
 * 备注：
 */
public interface QueryCondition {

    String apply(ParamMeta metaData, boolean isDynamic);


    default String dynamicIf(String conditionParam, String conditionQuery) {
        return "<if test=\"" + conditionParam + "\"> " + " AND " + conditionQuery + " </if>";
    }

    default String dynamicIsNull(String conditionParam, String conditionQuery) {
        return "( #{" + conditionParam + "} IS NULL OR " + conditionQuery + " )";
    }
}
