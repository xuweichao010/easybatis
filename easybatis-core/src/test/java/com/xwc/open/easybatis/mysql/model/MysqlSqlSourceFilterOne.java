package com.xwc.open.easybatis.mysql.model;

import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.enums.IdType;
import lombok.Data;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：
 */
@Data
public class MysqlSqlSourceFilterOne {

    private String id;

    /**
     * 机构编码
     */
    private String orgCode;
}
