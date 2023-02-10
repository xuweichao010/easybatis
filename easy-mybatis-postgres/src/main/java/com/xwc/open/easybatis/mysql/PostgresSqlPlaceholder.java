package com.xwc.open.easybatis.mysql;

import com.xwc.open.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/10 14:33
 */
public class PostgresSqlPlaceholder implements SqlPlaceholder {
    private static final String HOLDER = "\"";

    @Override
    public String holder(String column) {
        return HOLDER + column + HOLDER;
    }
}
