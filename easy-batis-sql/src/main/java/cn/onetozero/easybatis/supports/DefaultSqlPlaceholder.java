package cn.onetozero.easybatis.supports;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/16 15:34
 *
 */
public class DefaultSqlPlaceholder implements SqlPlaceholder {
    private static final String HOLDER = "`";

    @Override
    public String holder(String column) {
        return HOLDER + column + HOLDER;
    }
}
