package com.xwc.easy.core.table.column;

import cn.onetozero.easy.annotations.models.Column;

/**
 * 类描述： 没有注解
 * @author  徐卫超 (cc)
 * @since 2022/11/25 23:12
 */
public class ColumnModel {

    @Column(value = "alias_name", selectIgnore = true, updateIgnore = true, insertIgnore = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
