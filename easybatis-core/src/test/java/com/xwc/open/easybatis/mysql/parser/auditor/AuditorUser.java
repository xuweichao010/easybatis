package com.xwc.open.easybatis.mysql.parser.auditor;

import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.anno.table.auditor.CreateId;
import com.xwc.open.easybatis.core.anno.table.auditor.CreateName;
import com.xwc.open.easybatis.core.anno.table.auditor.CreateTime;
import com.xwc.open.easybatis.core.anno.table.auditor.UpdateId;
import com.xwc.open.easybatis.core.anno.table.auditor.UpdateName;
import com.xwc.open.easybatis.core.anno.table.auditor.UpdateTime;
import com.xwc.open.easybatis.core.enums.IdType;

import java.util.Date;

import lombok.Data;


@Data
@Table("t_user")
public class AuditorUser {
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
    @UpdateTime(selectIgnore = false)
    private Date updateTime;

    /**
     * 更新用户id
     */
    @UpdateId(selectIgnore = false)
    private String updateId;

    /**
     * 更新用户名
     */
    @UpdateName(selectIgnore = false)
    private String updateName;
}
