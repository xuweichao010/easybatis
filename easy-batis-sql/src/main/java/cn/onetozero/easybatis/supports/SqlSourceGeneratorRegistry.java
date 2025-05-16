package cn.onetozero.easybatis.supports;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/30 12:13
 */
public interface SqlSourceGeneratorRegistry {

    void registry(String databaseId, SqlSourceGenerator sourceGenerator, ParamArgsResolver paramArgsResolver);

    SqlSourceGenerator get(String databaseId);

    void remove(String databaseId);

    void clear();

    ParamArgsResolver getParamArgsResolver(String databaseId);
}
