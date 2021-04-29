package com.xwc.open.easybatis.assistant.model;

import com.xwc.open.easybatis.core.anno.table.auditor.CreateId;
import com.xwc.open.easybatis.core.anno.table.auditor.CreateName;
import com.xwc.open.easybatis.core.anno.table.auditor.CreateTime;
import com.xwc.open.easybatis.core.anno.table.auditor.UpdateId;
import com.xwc.open.easybatis.core.anno.table.auditor.UpdateName;
import com.xwc.open.easybatis.core.anno.table.auditor.UpdateTime;

import java.util.Date;

import lombok.Data;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：通用数据体
 */
@Data
public class BaseEntity {

    /**
     * 创建时间
     */
    @CreateTime
    private Date createTime;

    /**
     * 创建用户id
     */
    @CreateId
    private String createId;

    /**
     * 创建用户名
     */
    @CreateName
    private String createName;

    /**
     * 创建时间
     */
    @UpdateTime
    private Date updateTime;

    /**
     * 更新用户id
     */
    @UpdateId
    private String updateId;

    /**
     * 更新用户名
     */
    @UpdateName
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
