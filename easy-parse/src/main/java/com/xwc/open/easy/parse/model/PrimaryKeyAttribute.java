package com.xwc.open.easy.parse.model;

import com.xwc.open.easy.parse.enums.IdType;
import com.xwc.open.easy.parse.supports.IdGenerateHandler;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:48
 */
public class PrimaryKeyAttribute extends ModelAttribute {

    private IdType idType;

    private IdGenerateHandler idGenerateHandler;



    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public IdGenerateHandler getIdGenerateHandler() {
        return idGenerateHandler;
    }

    public void setIdGenerateHandler(IdGenerateHandler idGenerateHandler) {
        this.idGenerateHandler = idGenerateHandler;
    }
}
