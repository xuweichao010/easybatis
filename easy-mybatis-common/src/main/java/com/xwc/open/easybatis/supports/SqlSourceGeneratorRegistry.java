package com.xwc.open.easybatis.supports;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/30 12:13
 */
public interface SqlSourceGeneratorRegistry {

    void registry(String databaseId, SqlSourceGenerator sourceGenerator);

    SqlSourceGenerator get(String databaseId);

    void remove(String databaseId);

    void clear();
}
