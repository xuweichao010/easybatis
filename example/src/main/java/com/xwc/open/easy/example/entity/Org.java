package com.xwc.open.easy.example.entity;

import com.xwc.open.easy.batis.anno.table.Id;
import com.xwc.open.easy.batis.anno.table.Table;
import com.xwc.open.easy.batis.enums.IdType;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/26  22:09
 * 业务：
 * 功能：
 */
@Table("t_org")
public class Org  {

    /**
     * 机构编码;代码生成
     */
    @Id(type = IdType.CUSTOM)
    private String code;
    /**
     * 机构名称
     */
    private String name;
    /**
     * 父机构编码;顶级机构父机构代码为空
     */
    private String parentCode;
    /**
     * 父机构名称
     */
    private String parentName;
    /**
     * 地址信息
     */
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        return "Org{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", parentName='" + parentName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}