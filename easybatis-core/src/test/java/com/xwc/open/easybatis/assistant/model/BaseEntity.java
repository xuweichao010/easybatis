package com.xwc.open.easybatis.assistant.model;

import java.util.Date;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：通用数据体
 */
public class BaseEntity {

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建用户id
     */
    private String createId;

    /**
     * 创建用户名
     */
    private String createName;

    /**
     * 创建时间
     */
    private Date updateTime;

    /**
     * 更新用户id
     */
    private String updateId;

    /**
     * 更新用户名
     */
    private String updateName;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
}
