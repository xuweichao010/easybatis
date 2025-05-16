package com.xwc.easy.core.table.logic;

import cn.onetozero.easy.annotations.models.Logic;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/25 22:48
 */
public class LogicField {

    @Logic(invalid = "100", valid = "101")
    private String deleteFlag;

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
