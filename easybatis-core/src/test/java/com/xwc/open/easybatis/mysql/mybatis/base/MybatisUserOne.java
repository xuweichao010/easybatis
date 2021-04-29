package com.xwc.open.easybatis.mysql.mybatis.base;

import lombok.Data;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/30
 * 描述：查询条件
 */
@Data
public class MybatisUserOne {

    /**
     * 用户id
     */
    private String id;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;
}
