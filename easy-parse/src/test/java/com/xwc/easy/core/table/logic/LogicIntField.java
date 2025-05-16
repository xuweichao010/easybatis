package com.xwc.easy.core.table.logic;

import cn.onetozero.easy.annotations.models.Logic;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/25 22:48
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
