package com.xwc.esbatis.meta;

import com.xwc.esbatis.anno.enums.ConditionEnum;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/13  17:16
 * 业务：
 * 功能：属性
 */
public class FilterColumMate extends ColumMate {
    private ConditionEnum conditionEnum;
    private int index = 0;

    public FilterColumMate(String fieldName, String colunmName, ConditionEnum conditionEnum, int index) {
        super(fieldName, colunmName);
        this.conditionEnum = conditionEnum;
    }

    public FilterColumMate() {
    }

    public ConditionEnum getConditionEnum() {
        return conditionEnum;
    }

    public void setConditionEnum(ConditionEnum conditionEnum) {
        this.conditionEnum = conditionEnum;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }


}
