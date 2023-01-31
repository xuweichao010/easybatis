package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.utils.StringUtils;
import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.exceptions.NotFoundException;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/30 12:13
 */
public class DefaultSqlSourceGeneratorRegistry implements SqlSourceGeneratorRegistry {
    private EasyBatisConfiguration configuration;

    private final Map<String, SqlSourceGenerator> driverSqlSourceGeneratorMap = new ConcurrentHashMap<>();


    public DefaultSqlSourceGeneratorRegistry(EasyBatisConfiguration configuration) {
        this.configuration = configuration;
        this.registry(DriverDatabaseIdProvider.MYSQL, new DefaultSqlSourceGenerator(configuration));
    }

    @Override
    public void registry(String databaseId, SqlSourceGenerator sourceGenerator) {
        if (databaseId == null) {
            throw new NotFoundException("databaseId 不能为空");
        }
        if (sourceGenerator == null) {
            throw new NotFoundException("SqlSourceGenerator 不能为空");
        }
        this.driverSqlSourceGeneratorMap.put(databaseId, sourceGenerator);
    }

    @Override
    public SqlSourceGenerator get(String databaseId) {
        if (!StringUtils.hasText(databaseId)) {
            return defaultSqlSourceGenerator();
        }
        SqlSourceGenerator sqlSourceGenerator = driverSqlSourceGeneratorMap.get(databaseId);
        if (sqlSourceGenerator == null) {
            throw new NotFoundException("获取 SqlSourceGenerator 为空");
        }
        return sqlSourceGenerator;
    }

    private SqlSourceGenerator defaultSqlSourceGenerator() {
        for (DriverDatabaseIdProvider driverDatabaseIdProvider : configuration.getDriverDatabaseIdProviders()) {
            String databaseId = driverDatabaseIdProvider.databaseId();
            if (StringUtils.hasText(databaseId)) {
                return get(databaseId);
            }
        }
        String databaseId = getDriverDatabaseIdProvider();
        if (StringUtils.hasText(databaseId)) {
            return get(databaseId);
        }
        throw new NotFoundException("无法获取对应的数据库驱动");
    }

    private String getDriverDatabaseIdProvider() {
        try {
            Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverDatabaseIdProvider.MYSQL;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void remove(String databaseId) {
        driverSqlSourceGeneratorMap.remove(databaseId);
    }

    @Override
    public void clear() {
        driverSqlSourceGeneratorMap.clear();
    }
}
