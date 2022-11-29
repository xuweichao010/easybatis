package com.xwc.easy.core.table.column;

import com.xwc.open.easy.parse.annotations.Column;

/**
 * 类描述： 没有注解
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:12
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
