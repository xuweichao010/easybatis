package com.xwc.easy.core.table.fill;

import cn.onetozero.easy.annotations.models.FillColumn;
import cn.onetozero.easy.annotations.enums.FillType;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/25 22:48
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
