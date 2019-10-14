package com.xwc.open.esbatis.meta;


import com.xwc.open.esbatis.enums.AuditorType;
import com.xwc.open.esbatis.enums.IdType;
import org.apache.ibatis.binding.BindingException;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private LoglicAttribute logic;

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

    public String getTableName() {
        return tableName;
    }

    public List<Attribute> updateAttributeList() {
        ArrayList<Attribute> list = new ArrayList<>();
        list.addAll(attributeList);
        auditorAttributeList.forEach(auditor -> {
            if (auditor.getType().getGroup() == AuditorType.Group.UPDATE) list.add(auditor);
        });
        return list;
    }

    @Deprecated
    public String selectCloums() {
        StringBuilder sb = new StringBuilder();
        attributeList.forEach(attr -> sb.append(", ").append(attr.getColunm()));
        auditorAttributeList.stream().filter(attr -> !attr.isHidden()).forEach(attr -> sb.append(", ").append(attr.getColunm()));
        return sb.substring(1);
    }

    @Deprecated
    public String insertCloums() {
        StringBuilder sb = new StringBuilder();
        attributeList.forEach(attr -> sb.append(", ").append(attr.getColunm()));
        auditorAttributeList.stream().forEach(attr -> sb.append(", ").append(attr.getColunm()));
        return sb.substring(1);
    }

    @Deprecated
    public String insertField(String item) {
        StringBuilder sb = new StringBuilder();
        attributeList.forEach(attr -> sb.append(", ").append(attr.getBatisField(item)));
        auditorAttributeList.stream().forEach(attr -> sb.append(", ").append(attr.getColunm()));
        return sb.substring(1);
    }

    public List<Attribute> selectAttribute() {
        ArrayList<Attribute> list = new ArrayList<>();
        list.add(id);
        list.addAll(attributeList);
        list.addAll(auditorAttributeList.stream().filter(attr -> !attr.isHidden()).collect(Collectors.toList()));
        return list;
    }

    public List<Attribute> insertAttribute() {
        ArrayList<Attribute> list = new ArrayList<>();
        list.add(id);
        list.addAll(attributeList);
        list.addAll(auditorAttributeList);
        return list;
    }

    public List<Attribute> updateAttribute() {
        ArrayList<Attribute> list = new ArrayList<>();
        list.add(id);
        list.addAll(attributeList);
        list.addAll(auditorAttributeList.stream().filter(attr->attr.getType().getGroup() == AuditorType.Group.UPDATE)
                .collect(Collectors.toList()));
        return list;
    }


    public void setLogic(LoglicAttribute logic) {
        this.logic = logic;
    }

    public LoglicAttribute getLogic() {
        return logic;
    }

    public void addAuditorAttribute(AuditorAttribute attribute) {
        if (attribute == null) return;
        auditorAttributeList.add(attribute);
    }

    public List<AuditorAttribute> auditorAttributeList() {
        return auditorAttributeList;
    }

    public IdType getType() {
        return type;
    }


    public EntityMate validate(Class<?> clazz) {
        if (tableName == null && tableName.isEmpty()) {
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
