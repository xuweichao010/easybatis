package com.xwc.open.easybatis.assistant.model;


import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.anno.table.fill.CreateId;
import com.xwc.open.easybatis.core.anno.table.fill.CreateName;
import com.xwc.open.easybatis.core.anno.table.fill.CreateTime;
import com.xwc.open.easybatis.core.anno.table.fill.UpdateId;
import com.xwc.open.easybatis.core.anno.table.fill.UpdateName;
import com.xwc.open.easybatis.core.anno.table.fill.UpdateTime;
import com.xwc.open.easybatis.core.enums.IdType;

import java.util.Date;

import lombok.Data;

@Table("t_user")
@Data
public class AuditUser {

    /**
     * 用户id
     */
    @Id(type = IdType.UUID)
    private String id;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

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
    @UpdateName(value = "update_name01", selectIgnore = false)
    private String updateName;

}
