package com.xwc.easy.core.table.method;

import cn.onetozero.easy.annotations.models.Id;
import cn.onetozero.easy.annotations.models.Table;
import cn.onetozero.easy.annotations.enums.IdType;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/27 20:57
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
