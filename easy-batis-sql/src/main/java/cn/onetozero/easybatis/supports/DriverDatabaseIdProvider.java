package cn.onetozero.easybatis.supports;

import java.util.Comparator;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/31 9:58
 */
public abstract class DriverDatabaseIdProvider implements Comparator<DriverDatabaseIdProvider> {

    public static final String MYSQL = "mysql";

    public abstract String databaseId();

    public abstract int order();

    @Override
    public int compare(DriverDatabaseIdProvider o1, DriverDatabaseIdProvider o2) {
        return o1.order() - o2.order();
    }
}
