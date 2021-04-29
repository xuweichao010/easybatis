package com.xwc.open.easybatis.assistant.model;


import com.xwc.open.easybatis.core.anno.table.Column;
import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.enums.IdType;
import lombok.Data;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/26  22:09
 * 业务：
 * 功能：
 */
@Table("t_table_column")
@Data
public class TableColumn {
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
    @Column("address_column")
    private String address;

    /**
     * 组织信息
     */
    @Column(selectIgnore = true, updateIgnore = true, insertIgnore = true)
    private Integer employeesNum;

    @Ignore
    private Integer ignore;


}