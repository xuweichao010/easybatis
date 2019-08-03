package com.xwc.open.esbatis.meta;


import com.xwc.open.esbatis.enums.IdType;
import org.apache.ibatis.binding.BindingException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/13  17:09
 * 业务：
 * 功能：记录实体的信息
 */
public class EntityMate {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 主键信息
     */
    private Attribute id;

    /**
     * 主键类型
     */
    private IdType type;

    /**
     * 属性信息
     */
    private List<Attribute> attributeList = new ArrayList<>(10);

    private List<AuditorAttribute> auditorAttributeList = new ArrayList<>(6);

    /**
     * 属性信息
     */
    private Attribute logic;

    public EntityMate(String tableName) {
        this.tableName = tableName;
    }

    public void addAttribute(Attribute mate) {
        attributeList.add(mate);
    }

    public void setId(Attribute id, IdType type) {
        this.id = id;
        this.type = type;
    }

    public void addAuditorAttribute(AuditorAttribute attribute) {
        if (attribute == null) return;
        auditorAttributeList.add(attribute);
    }


    public IdType getType() {
        return type;
    }


    public EntityMate validate(Class<?> clazz) {
        if (StringUtils.isEmpty(tableName)) {
            throw new BindingException(clazz.toString() + ": 获取不到表名 ");
        }
        if (id == null) {
            throw new BindingException(clazz.toString() + ": 获取不到主键 ");
        }
        return this;
    }

    public Attribute getId() {
        return id;
    }
}
