package com.xwc.easy.core.table.fill;

import com.xwc.open.easy.core.annotations.FillColumn;
import com.xwc.open.easy.core.enums.FillType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 22:48
 */
public class FillFieldModel {
    @FillColumn(selectIgnore = true, type = FillType.MODIFY)
    private String createUserId;

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}
