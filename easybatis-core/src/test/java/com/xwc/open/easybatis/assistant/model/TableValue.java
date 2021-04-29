package com.xwc.open.easybatis.assistant.model;


import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Table;
import lombok.Data;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/26  22:09
 * 业务：
 * 功能：
 */
@Table("t_table")
@Data
public class TableValue {
    @Id
    private Long id;
}