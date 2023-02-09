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
public class EasyBatisConfiguration extends Configuration {

    protected final EasyConfiguration easyConfiguration;

    protected final EasyMapperRegister mapperRegistry = new EasyMapperRegister(this);

    private final List<DriverDatabaseIdProvider> driverDatabaseIdProviders = new ArrayList<>();

    private final Map<String, OperateMethodMeta> operateMethodMetaMaps = new HashMap<>();

    private final List<FillAttributeHandler> fillAttributeHandlers = new ArrayList<>();

    protected final SqlSourceGeneratorRegistry registry = new DefaultSqlSourceGeneratorRegistry(this);

    {
        this.driverDatabaseIdProviders.addAll(Collections.singletonList(new MysqlDriverDatabaseIdProvider()));
    }

    public EasyBatisConfiguration(EasyConfiguration easyConfiguration) {
        this.easyConfiguration = easyConfiguration;
        if (this.isMapUnderscoreToCamelCase()) {
            easyConfiguration.setColumnNameConverter(new CamelConverterUnderscore());
            easyConfiguration.setTableNameConverter(new CamelConverterUnderscore());
        } else {
            easyConfiguration.setColumnNameConverter(new NoneNameConverter());
            easyConfiguration.setTableNameConverter(new NoneNameConverter());
        }
    }


    public EasyConfiguration getEasyConfiguration() {
        return easyConfiguration;
    }

    public List<FillAttributeHandler> getFillAttributeHandlers() {
        return fillAttributeHandlers;
    }

    public void addFillAttributeHandler(FillAttributeHandler fillAttributeHandler) {
        this.fillAttributeHandlers.add(fillAttributeHandler);
    }

    public EasyConfiguration getConfiguration() {
        return easyConfiguration;
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


    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        if (mapUnderscoreToCamelCase) {
            this.easyConfiguration.setColumnNameConverter(new CamelConverterUnderscore());
            this.easyConfiguration.setTableNameConverter(new CamelConverterUnderscore());
        } else {
            this.easyConfiguration.setColumnNameConverter(new NoneNameConverter());
            this.easyConfiguration.setTableNameConverter(new NoneNameConverter());
        }
    }

    @Override
    public String getDatabaseId() {
        return super.getDatabaseId();
    }

    @Override
    public void setDatabaseId(String databaseId) {
        if (StringUtils.hasText(databaseId)) {
            super.setDatabaseId(databaseId);
        } else {
            for (DriverDatabaseIdProvider provider : driverDatabaseIdProviders) {
                databaseId = provider.databaseId();
                if (StringUtils.hasText(databaseId)) {
                    this.databaseId = databaseId;
                    return;
                }
            }
        }
        super.setDatabaseId(databaseId);
    }

    public void addMappers(String packageName, Class<?> superType) {
        mapperRegistry.addMappers(packageName, superType);
    }

    public void addMappers(String packageName) {
        mapperRegistry.addMappers(packageName);
    }

    public <T> void addMapper(Class<T> type) {
        mapperRegistry.addMapper(type);
    }

    public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
        return mapperRegistry.getMapper(type, sqlSession);
    }

    public boolean hasMapper(Class<?> type) {
        return mapperRegistry.hasMapper(type);
    }


}