package com.xwc.open.easybatis.supports;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/6 13:37
 */
public class MysqlDriverDatabaseIdProvider extends DriverDatabaseIdProvider {
    @Override
    public String databaseId() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverDatabaseIdProvider.MYSQL;
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }
    @Override
    public int order() {
        return 0;
    }
}
