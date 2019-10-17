package com.xwc.open.easy.example.entity;

import com.xwc.open.easy.batis.anno.table.Id;
import com.xwc.open.easy.batis.anno.table.Loglic;
import com.xwc.open.easy.batis.anno.table.Table;
import com.xwc.open.easy.batis.enums.IdType;

import java.io.Serializable;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/27  10:17
 * 业务：测试逻辑删除功能实体
 * 功能：
 *
 */
@Table("t_user")
public class UserLogic implements Serializable {
    private static final long serialVersionUID = -4279599274719815691L;
    @Id(type = IdType.UUID)
    private String id;
    private String name;

    /**
     * 是否有效
     */
    @Loglic(valid = 0, invalid = 1)
    private Integer valid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return "UserLogic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
