package com.xwc.open.easy.core.model;

/**
 * 类描述：用于存储数据库逻辑删除的字段
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:50
 */
public class LogicAttribute extends ModelAttribute{

    private int valid;

    private int invalid;

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getInvalid() {
        return invalid;
    }

    public void setInvalid(int invalid) {
        this.invalid = invalid;
    }
}
