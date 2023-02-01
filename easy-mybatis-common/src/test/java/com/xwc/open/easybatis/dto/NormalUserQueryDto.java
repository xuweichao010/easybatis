package com.xwc.open.easybatis.dto;

import com.xwc.open.easybatis.annotaions.conditions.Between;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.annotaions.page.Limit;
import com.xwc.open.easybatis.annotaions.page.Offset;
import lombok.Data;

/**
 * 类描述：用于单元测试对象查询
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 9:37
 */

@Data
public class NormalUserQueryDto {
    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 用户名
     */
    @Equal(dynamic = true)
    private String name;

    /**
     * 用户年龄
     */
    @Between(of = "ageTo")
    private Integer age;

    /**
     * 用户年龄
     */
    private Integer ageTo;

    /**
     * 职位 1-总监 2-经理 1-主管 3-销售 4-行政 5-技术员 6-财务
     */
    @Equal(dynamic = true)
    private Integer job;

    /**
     *
     */
    @Limit
    private int limit = 10;

    @Offset
    private int offset = 0;


}
