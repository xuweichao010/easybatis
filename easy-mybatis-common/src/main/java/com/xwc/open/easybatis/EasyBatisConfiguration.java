package com.xwc.open.easybatis;


import com.xwc.open.easy.parse.EasyConfiguration;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easy.parse.supports.impl.CamelConverterUnderscore;
import com.xwc.open.easy.parse.supports.impl.NoneNameConverter;
import com.xwc.open.easybatis.snippet.values.EasyMapperRegister;
import org.apache.ibatis.binding.MapperRegistry;
import org.apache.ibatis.session.Configuration;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/12/2 10:16
 */
public class EasyBatisConfiguration extends EasyConfiguration {

    protected final Configuration configuration;

    protected final EasyMapperRegister mapperRegistry = new EasyMapperRegister(this);


    public EasyBatisConfiguration(Configuration configuration) {
        this.configuration = configuration;
        if (this.configuration.isMapUnderscoreToCamelCase()) {
            this.setColumnNameConverter(new CamelConverterUnderscore());
            this.setTableNameConverter(new CamelConverterUnderscore());
        } else {
            this.setColumnNameConverter(new NoneNameConverter());
            this.setTableNameConverter(new NoneNameConverter());
        }
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void addMapper(Class<?> type) {
        configuration.addMapper(type);
        mapperRegistry.addMapper(type);
    }

    public void setMapUnderscoreToCamelCase(boolean mapUnderscoreToCamelCase) {
        this.configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
    }
}