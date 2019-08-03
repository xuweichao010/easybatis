package com.xwc.open.example.entity;

import java.io.Serializable;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/26  22:09
 * 业务：
 * 功能：
 */
public class Org extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1288582698027505711L;
    /**
     * 机构编码;代码生成
     */
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
     * 负责人
     */
    private String linkman;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 联系方式
     */
    private String telephone;
    /**
     * 位置
     */
    private String address;
    /**
     * 子机构的数量;用于生成机构代码
     */
    private Integer sons;

    /**
     * 同步Id
     */
    private String syncId;



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

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSons() {
        return sons;
    }

    public void setSons(Integer sons) {
        this.sons = sons;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    @Override
    public Integer getValid() {
        return super.getValid();
    }
}