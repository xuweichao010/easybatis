package com.xwc.easy.core.table.key;

import com.xwc.open.easy.core.annotations.Id;
import com.xwc.open.easy.core.enums.IdType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 9:49
 */
public class HandlerPrimaryKey {

    @Id(type = IdType.HANDLER)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
