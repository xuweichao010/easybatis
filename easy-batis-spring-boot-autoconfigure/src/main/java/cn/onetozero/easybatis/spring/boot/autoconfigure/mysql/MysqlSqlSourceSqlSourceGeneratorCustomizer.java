package cn.onetozero.easybatis.spring.boot.autoconfigure.mysql;


import cn.onetozero.easybatis.mysql.MysqlSqlSourceGenerator;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/10 14:53
 */
@FunctionalInterface
public interface MysqlSqlSourceSqlSourceGeneratorCustomizer {

    void customize(MysqlSqlSourceGenerator mysqlSqlSourceGenerator);
}
