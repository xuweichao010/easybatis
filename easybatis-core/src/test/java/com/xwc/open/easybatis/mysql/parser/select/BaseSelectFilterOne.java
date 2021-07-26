package com.xwc.open.easybatis.mysql.parser.select;

import com.xwc.open.easybatis.core.anno.condition.filter.In;
import lombok.Data;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：
 */
@Data
public class BaseSelectFilterOne {

    private String id;

    /**
     * 机构编码
     */
    private String orgCode;

    @In("id")
    private String ids;

}
