package com.xwc.open.easybatis.core.interfaces.condition;

import com.xwc.open.easybatis.core.enums.DynamicType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.interfaces.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.support.ParamMeta;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:07
 * 备注：
 */
public interface QueryCondition extends MyBatisOrSqlTemplate {

    String apply(ParamMeta metaData, boolean multi);


    default String doApply(String conditionParam, String conditionQuery, DynamicType type) {
        if (type == DynamicType.NO_DYNAMIC) {
            return "AND" + conditionQuery;
        } else if (type == DynamicType.DYNAMIC) {
            return dynamicConditionIf(conditionParam, conditionQuery);
        } else if (type == DynamicType.GUISE_DYNAMIC) {
            return guiseDynamicConditionIf( conditionQuery);
        } else {
            throw new EasyBatisException("无法支持的动态语句");
        }
    }




}
