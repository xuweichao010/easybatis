package com.xwc.open.easybatis;


import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easy.parse.supports.impl.CamelConverterUnderscore;
import com.xwc.open.easy.parse.supports.impl.NoneNameConverter;
import com.xwc.open.easybatis.snippet.values.EasyMapperRegister;
import com.xwc.open.easybatis.supports.DefaultSqlSourceGeneratorRegistry;
import com.xwc.open.easybatis.supports.DriverDatabaseIdProvider;
import com.xwc.open.easybatis.supports.SqlSourceGenerator;
import com.xwc.open.easybatis.supports.SqlSourceGeneratorRegistry;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/12/2 10:16
 */
public class EasyBatisConfiguration extends EasyConfiguration {

    protected final Configuration configuration;

    protected final EasyMapperRegister mapperRegistry = new EasyMapperRegister(this);
    protected final Set<String> loadedResources = new HashSet<>();

    private final List<DriverDatabaseIdProvider> driverDatabaseIdProviders = new ArrayList<>();

    protected final SqlSourceGeneratorRegistry registry = new DefaultSqlSourceGeneratorRegistry(this);


    public EasyBatisConfiguration(Configuration configuration) {
        this.configuration = configuration;
        this.setMapUnderscoreToCamelCase(configuration.isMapUnderscoreToCamelCase());
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void addMapper(Class<?> type) {
        configuration.addMapper(type);
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return configuration.getMapperRegistry().getMapper(type, sqlSession);
    }


    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        if (this.configuration.isMapUnderscoreToCamelCase()) {
            this.setColumnNameConverter(new CamelConverterUnderscore());
            this.setTableNameConverter(new CamelConverterUnderscore());
        } else {
            this.setColumnNameConverter(new NoneNameConverter());
            this.setTableNameConverter(new NoneNameConverter());
        }
    }

    public List<DriverDatabaseIdProvider> getDriverDatabaseIdProviders() {
        return driverDatabaseIdProviders;
    }

    public void addLoadedResource(String resource) {
        loadedResources.add(resource);
    }

    public boolean isResourceLoaded(String resource) {
        return loadedResources.contains(resource);
    }


    public void registrySqlSourceGenerator(String databaseId, SqlSourceGenerator sourceGenerator) {
        this.registry.registry(databaseId, sourceGenerator);
    }

    public SqlSourceGenerator getSqlSourceGenerator(String databaseId) {
        return this.registry.get(databaseId);
    }

    public void removeSqlSourceGenerator(String databaseId) {
        this.registry.remove(databaseId);
    }


    public void clearSqlSourceGenerator() {
        this.registry.clear();
    }
}