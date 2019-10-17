package com.xwc.open.example.entity;

import com.xwc.open.easybatis.anno.table.Id;
import com.xwc.open.easybatis.anno.table.Table;
import com.xwc.open.easybatis.enums.IdType;

import java.io.Serializable;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/27  10:17
 * 业务：测试UUID实体
 * 功能：
 */
@Table("t_user")
public class User implements Serializable {
    private static final long serialVersionUID = -4279599274719815691L;
    @Id(type = IdType.UUID)
    private String id;

    private String name;


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

    @Override
    public String toString() {
        return "UserLogic{" +
                "id='" + id + '\'' +
                '}';
    }
}
