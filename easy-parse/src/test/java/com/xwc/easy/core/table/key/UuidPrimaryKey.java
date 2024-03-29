package com.xwc.easy.core.table.key;

import cn.onetozero.easy.parse.annotations.Id;
import cn.onetozero.easy.parse.enums.IdType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 9:49
 */
public class UuidPrimaryKey {

    @Id(type = IdType.UUID)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
