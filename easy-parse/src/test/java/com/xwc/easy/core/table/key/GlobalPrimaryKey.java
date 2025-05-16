package com.xwc.easy.core.table.key;

import cn.onetozero.easy.annotations.models.Id;
import cn.onetozero.easy.annotations.enums.IdType;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/25 9:49
 */
public class GlobalPrimaryKey {

    @Id(type = IdType.GLOBAL)
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
