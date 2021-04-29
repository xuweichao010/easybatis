package com.xwc.open.easybatis.assistant.method;

import com.xwc.open.easybatis.core.anno.condition.filter.IsNull;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/18
 * 描述：
 */
public class ParseMethodParamQuery {

    private String condition1;

    @IsNull("condition2_column")
    private String condition2;

    public String getCondition1() {
        return condition1;
    }

    public void setCondition1(String condition1) {
        this.condition1 = condition1;
    }

    public String getCondition2() {
        return condition2;
    }

    public void setCondition2(String condition2) {
        this.condition2 = condition2;
    }
}
