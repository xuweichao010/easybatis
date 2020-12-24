package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.enums.ParamType;
import com.xwc.open.easybatis.core.support.ParamMeta;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:07
 * 备注：
 */
public interface QueryCondition extends MyBatisOrSqlTemplate {

    String apply(ParamMeta metaData);


    default String doApply(String conditionParam, String conditionQuery, ParamType type) {
        if (type == ParamType.FILED_TYPE || type == ParamType.PARAM_TYPE) {
            return conditionQuery;
        } else if (type == ParamType.FILED_TYPE_DYNAMIC) {
            return dynamicConditionIf(conditionParam, conditionQuery);
        } else if (type == ParamType.PARAM_TYPE_DYNAMIC) {
            return dynamicConditionIsNull(conditionParam, conditionQuery);
        } else {
            return null;
        }
    }
}
