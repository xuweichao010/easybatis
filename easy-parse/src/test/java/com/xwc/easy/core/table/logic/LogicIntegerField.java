package com.xwc.easy.core.table.logic;

import cn.onetozero.easy.annotations.models.Logic;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/25 22:48
 */
public class LogicIntegerField {

    @Logic(invalid = "100", valid = "101")
    private Integer deleteFlag;

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
}
