package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.utils.StringUtils;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.exceptions.NotFoundException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述：sql语句构建器 管理不通厂商的SQL语句构建构建功能
 * 作者：徐卫超 (cc)
 * 时间 2023/1/30 12:13
 */
public class DefaultSqlSourceGeneratorRegistry implements SqlSourceGeneratorRegistry {
    private final EasyBatisConfiguration configuration;

    private final Map<String, SqlSourceGenerator> driverSqlSourceGeneratorMap = new ConcurrentHashMap<>();
    private final Map<String, ParamArgsResolver> driverParamArgsResolverMap = new ConcurrentHashMap<>();


    public DefaultSqlSourceGeneratorRegistry(EasyBatisConfiguration configuration) {
        this.configuration = configuration;
        this.registry(DriverDatabaseIdProvider.MYSQL, new DefaultSqlSourceGenerator(configuration),
                new DefaultParamArgsResolver(configuration));
    }

    @Override
    public void registry(String databaseId, SqlSourceGenerator sourceGenerator, ParamArgsResolver paramArgsResolver) {
        if (databaseId == null) {
            throw new NotFoundException("databaseId 不能为空");
        }
        if (sourceGenerator == null) {
            throw new NotFoundException("SqlSourceGenerator 不能为空");
        }
        this.driverSqlSourceGeneratorMap.put(databaseId, sourceGenerator);
        this.driverParamArgsResolverMap.put(databaseId, paramArgsResolver);
    }

    @Override
    public SqlSourceGenerator get(String databaseId) {
        if (!StringUtils.hasText(databaseId)) {
            databaseId = configuration.getDatabaseId();
        }
        SqlSourceGenerator sqlSourceGenerator = driverSqlSourceGeneratorMap.get(databaseId);
        if (sqlSourceGenerator == null) {
            throw new NotFoundException("获取 SqlSourceGenerator 为空");
        }
        return sqlSourceGenerator;
    }


    @Override
    public void remove(String databaseId) {
        driverSqlSourceGeneratorMap.remove(databaseId);
    }

    @Override
    public void clear() {
        driverSqlSourceGeneratorMap.clear();
    }

    @Override
    public ParamArgsResolver getParamArgsResolver(String databaseId) {
        if (!StringUtils.hasText(databaseId)) {
            databaseId = configuration.getDatabaseId();
        }
        ParamArgsResolver paramArgsResolver = driverParamArgsResolverMap.get(databaseId);
        if (paramArgsResolver == null) {
            throw new NotFoundException("获取 ParamArgsResolver 为空");
        }
        return paramArgsResolver;
    }
}
