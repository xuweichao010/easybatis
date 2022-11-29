package com.xwc.easy.core.table.logic;

import com.xwc.open.easy.parse.annotations.Logic;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 22:48
 */
public class LogicField {

    @Logic(invalid = 100, valid = 101)
    private String deleteFlag;

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
