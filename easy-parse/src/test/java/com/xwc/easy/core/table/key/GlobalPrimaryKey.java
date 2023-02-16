package com.xwc.easy.core.table.key;

import cn.onetozero.easy.parse.annotations.Id;
import cn.onetozero.easy.parse.enums.IdType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 9:49
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
