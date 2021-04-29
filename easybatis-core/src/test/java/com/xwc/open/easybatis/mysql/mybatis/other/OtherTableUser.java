package com.xwc.open.easybatis.mysql.mybatis.other;

import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.enums.IdType;
import lombok.Data;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户信息表
 */
@Data
@Table("${tableName}")
public class OtherTableUser {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @Id(type = IdType.CUSTOM)
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
     * 用户名
     */
    private String name;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 职位 1-总监 2-经理 1-主管 3-销售 4-行政 5-技术员 6-财务
     */
    private Integer job;

    /**
     * 是否有效 0:有效 1:无效
     */
    private Integer valid;
}
