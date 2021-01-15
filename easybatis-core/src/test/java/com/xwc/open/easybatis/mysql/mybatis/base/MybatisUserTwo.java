package com.xwc.open.easybatis.mysql.mybatis.base;

import lombok.Data;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/30
 * 描述：查询条件
 */
@Data
public class MybatisUserTwo {

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 职位 1-总监 2-经理 1-主管 3-销售 4-行政 5-技术员 6-财务
     */
    private Integer job;

    /**
     * 是否有效 0:有效 1:无效
     */
    private Integer valid;
}
