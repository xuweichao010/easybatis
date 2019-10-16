package com.xwc.open.easy.example.entity;

import com.xwc.open.easy.batis.anno.table.Id;
import com.xwc.open.easy.batis.anno.table.Table;
import com.xwc.open.easy.batis.enums.IdType;
import lombok.Data;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/26  22:09
 * 业务：
 * 功能：
 */
@Table("t_org")
@Data
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

    /**
     * 组织信息
     */
    private Integer employeesNum;
}