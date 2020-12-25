package com.xwc.open.easybatis.core.interfaces.impl;

import com.xwc.open.easybatis.core.interfaces.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.interfaces.UpdateConditionSnippet;
import com.xwc.open.easybatis.core.support.MethodMeta;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：
 */
public class DefaultUpdateConditionSnippet implements UpdateConditionSnippet, MyBatisOrSqlTemplate {
    @Override
    public String apply(MethodMeta methodMeta) {
        return null;
    }
}
