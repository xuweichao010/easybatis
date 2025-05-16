package cn.onetozero.easybatis.mysql;

import cn.onetozero.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/10 14:33
 */
public class PostgresSqlPlaceholder implements SqlPlaceholder {
    private static final String HOLDER = "\"";

    @Override
    public String holder(String column) {
        return HOLDER + column + HOLDER;
    }
}
