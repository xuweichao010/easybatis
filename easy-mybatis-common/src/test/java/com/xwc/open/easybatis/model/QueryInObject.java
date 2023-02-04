package com.xwc.open.easybatis.model;

import com.xwc.open.easybatis.annotaions.conditions.In;
import lombok.Data;

import java.util.List;

/**
 * 类描述：对象IN查询
 * 作者：徐卫超 (cc)
 * 时间 2023/2/4 9:53
 */
@Data
public class QueryInObject {

    @In("age")
    private List<Integer> ages;

    private String id;
}
