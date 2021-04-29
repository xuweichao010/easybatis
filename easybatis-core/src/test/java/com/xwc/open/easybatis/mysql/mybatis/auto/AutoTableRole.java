package com.xwc.open.easybatis.mysql.mybatis.auto;

import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.enums.IdType;
import lombok.Data;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/3/25
 * 描述：
 */

@Data
@Table("${tableName}")
public class AutoTableRole {

    /**
     * 角色id
     */
    @Id(type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

}
