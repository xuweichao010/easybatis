package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.enums.DynamicType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.ParamMapping;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:07
 * 备注：
 */
public interface QueryCondition extends MyBatisOrSqlTemplate {

    String apply(ParamMapping metaData, boolean multi);


    default String doApply(String conditionParam, String conditionQuery, DynamicType type) {
        if (type == DynamicType.NO_DYNAMIC) {
            return " AND " + conditionQuery;
        } else if (type == DynamicType.DYNAMIC) {
            return dynamicAndConditionIf(conditionParam, conditionQuery);
        } else if (type == DynamicType.GUISE_DYNAMIC) {
            return guiseDynamicAndConditionIf( conditionQuery);
        } else {
            throw new EasyBatisException("无法支持的动态语句");
        }
    }




}
