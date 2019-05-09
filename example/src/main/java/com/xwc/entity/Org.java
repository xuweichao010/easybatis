package com.xwc.entity;

import com.xwc.esbatis.anno.enums.KeyEnum;
import com.xwc.esbatis.anno.table.PrimaryKey;
import com.xwc.esbatis.anno.table.Table;

import java.io.Serializable;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/26  22:09
 * 业务：
 * 功能：
 */
@Table("t_org")
@SuppressWarnings("unused")
public class Org implements Serializable {

    private static final long serialVersionUID = -1288582698027505711L;
    /**
     * 机构编码;代码生成
     */
    @PrimaryKey(type = KeyEnum.CUSTOM)
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
     * 子机构的数量;用于生成机构代码
     */
    private Integer sons = 0;


    /**
     * 机构编码;代码生成
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 机构编码;代码生成
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 机构名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 机构名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 父机构编码;顶级机构父机构代码为空
     */
    public String getParentCode() {
        return this.parentCode;
    }

    /**
     * 父机构编码;顶级机构父机构代码为空
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    /**
     * 父机构名称
     */
    public String getParentName() {
        return this.parentName;
    }

    /**
     * 父机构名称
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * 子机构的数量;用于生成机构代码
     */
    public Integer getSons() {
        return this.sons;
    }

    /**
     * 子机构的数量;用于生成机构代码
     */
    public void setSons(Integer sons) {
        this.sons = sons;
    }


}
