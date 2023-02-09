package com.xwc.open.easybatis;


import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.supports.impl.CamelConverterUnderscore;
import com.xwc.open.easy.parse.supports.impl.NoneNameConverter;
import com.xwc.open.easy.parse.utils.StringUtils;
import com.xwc.open.easybatis.fill.FillAttributeHandler;
import com.xwc.open.easybatis.ibatis.EasyMapperRegister;
import com.xwc.open.easybatis.supports.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

import java.util.*;

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

    private final Map<String, OperateMethodMeta> operateMethodMetaMaps = new HashMap<>();

    private final List<FillAttributeHandler> fillAttributeHandlers = new ArrayList<>();

    protected final SqlSourceGeneratorRegistry registry = new DefaultSqlSourceGeneratorRegistry(this);

    private String defaultDatabaseId = null;

    {
        this.driverDatabaseIdProviders.addAll(Collections.singletonList(new MysqlDriverDatabaseIdProvider()));
    }

    public EasyBatisConfiguration(Configuration configuration) {
        this.configuration = configuration;
        this.setMapUnderscoreToCamelCase(configuration.isMapUnderscoreToCamelCase());
        this.setDefaultDatabaseId(null);
    }

    public void setDefaultDatabaseId(String defaultDatabaseId) {
        if (StringUtils.hasText(defaultDatabaseId)) {
            this.defaultDatabaseId = defaultDatabaseId;
        } else {
            for (DriverDatabaseIdProvider provider : driverDatabaseIdProviders) {
                String databaseId = provider.databaseId();
                if (StringUtils.hasText(databaseId)) {
                    this.defaultDatabaseId = databaseId;
                    return;
                }
            }
        }
    }

    public String getDefaultDatabaseId() {
        return defaultDatabaseId;
    }

    public List<FillAttributeHandler> getFillAttributeHandlers() {
        return fillAttributeHandlers;
    }

    public void addFillAttributeHandler(FillAttributeHandler fillAttributeHandler) {
        this.fillAttributeHandlers.add(fillAttributeHandler);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void addMapper(Class<?> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
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

    public void addOperateMethodMeta(String id, OperateMethodMeta operateMethodMeta) {
        operateMethodMetaMaps.put(id, operateMethodMeta);
    }

    public OperateMethodMeta getOperateMethodMeta(String id) {
        return operateMethodMetaMaps.get(id);
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


    public void registrySqlSourceGenerator(String databaseId, SqlSourceGenerator sourceGenerator, ParamArgsResolver paramArgsResolver) {
        this.registry.registry(databaseId, sourceGenerator, paramArgsResolver);
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


    public ParamArgsResolver getParamArgsResolver(String databaseId) {
        return this.registry.getParamArgsResolver(databaseId);
    }
}