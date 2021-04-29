package com.xwc.open.easybatis.mysql.mybatis.logic;

import lombok.Data;

@Data
public class UserFilter {
    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构名称
     */
    private String orgName;
}
