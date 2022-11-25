package com.xwc.open.easy.core.model;

import com.xwc.open.easy.core.enums.IdType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:48
 */
public class PrimaryKeyAttribute extends ModelAttribute {

    private IdType idType;

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }
}
