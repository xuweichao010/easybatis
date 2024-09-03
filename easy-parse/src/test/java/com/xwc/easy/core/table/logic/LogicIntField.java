package com.xwc.easy.core.table.logic;

import cn.onetozero.easy.parse.annotations.Logic;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 22:48
 */
public class LogicIntField {

    @Logic(invalid = "100", valid = "101")
    private int deleteFlag;

    public int getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(int deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
