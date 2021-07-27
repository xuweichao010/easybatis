package com.xwc.open.easybatis.mysql.parser.select;

import com.xwc.open.easybatis.core.anno.condition.filter.In;
import lombok.Data;

import java.util.Set;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/7/27
 * 描述：
 */
@Data
public class BaseSelectFilterThree {

    private String id;

    /**
     * 机构编码
     */
    private String orgCode;


    @In("id")
    private Set<String> ids;
}
