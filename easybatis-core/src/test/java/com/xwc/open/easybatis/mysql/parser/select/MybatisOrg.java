package com.xwc.open.easybatis.mysql.parser.select;

import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Logic;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.enums.IdType;
import lombok.Data;

/**
 * 创建人：徐卫超 CC
 * 时间：2021/3/27 21:19
 * 备注：
 */
@Table("t_org")
@Data
public class MybatisOrg {

    /**
     * 机构编码
     */
    @Id(type = IdType.AUTO)
    private String code;

    /**
     * 机构名称
     */
    private String name;

    /**
     * 上级机构编码
     */
    private String parent_code;

    /**
     * 上级机构名称
     */
    private String parent_name;

    /**
     * 机构地址
     */
    private String address;

    /**
     * 组织下的员工人数
     */
    private Integer employees_num;

    @Logic(invalid = 0, valid = 1)
    private Integer valid;
}
