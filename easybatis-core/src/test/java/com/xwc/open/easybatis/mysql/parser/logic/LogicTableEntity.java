package com.xwc.open.easybatis.mysql.parser.logic;

import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Logic;
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
public class LogicTableEntity {

    private static final long serialVersionUID = 1L;

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
     * 是否有效 0:有效 1:无效
     */
    @Logic(valid = 1, invalid = 0)
    private Integer valid;

}
