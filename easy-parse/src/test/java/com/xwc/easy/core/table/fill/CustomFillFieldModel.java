package com.xwc.easy.core.table.fill;

import cn.onetozero.easy.parse.annotations.FillColumn;
import cn.onetozero.easy.parse.enums.FillType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 22:48
 */
public class CustomFillFieldModel {
    @FillColumn(selectIgnore = true, type = FillType.INSERT_UPDATE, identification = "username")
    private String createUserId;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}
