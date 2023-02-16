package com.xwc.easy.core.table.method;

import cn.onetozero.easy.parse.annotations.Id;
import cn.onetozero.easy.parse.annotations.Table;
import cn.onetozero.easy.parse.enums.IdType;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/27 20:57
 */
@Table("t_test")
public class MethodEntity {

    @Id(type = IdType.AUTO)
    private Long id;

    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
