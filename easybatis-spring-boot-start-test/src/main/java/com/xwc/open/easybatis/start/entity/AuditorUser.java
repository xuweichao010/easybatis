package com.xwc.open.easybatis.start.entity;

import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.anno.table.fill.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/4/23
 * 描述：用户中心
 */

@Data
@Table("t_user")
@EqualsAndHashCode(callSuper = true)
public class AuditorUser extends LogicUser{

    @CreateTime
    private Date createTime; //创建时间

    @CreateId
    private String createId; //创建用户id

    @CreateName
    private String createName; // 创建用户名

    @UpdateTime //创建时间
    private Date updateTime;

    @UpdateId //更新用户id
    private String updateId;

    @UpdateName //更新用户名
    private String updateName;
}
