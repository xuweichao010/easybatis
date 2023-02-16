package cn.onetozero.easybatis.supports;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 15:34
 */
public class DefaultSqlPlaceholder implements SqlPlaceholder {
    private static final String HOLDER = "`";

    @Override
    public String holder(String column) {
        return HOLDER + column + HOLDER;
    }
}
