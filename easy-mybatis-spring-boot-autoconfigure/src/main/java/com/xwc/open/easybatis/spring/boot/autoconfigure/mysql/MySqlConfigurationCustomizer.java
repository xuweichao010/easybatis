package com.xwc.open.easybatis.spring.boot.autoconfigure.mysql;

import com.xwc.open.easybatis.EasyBatisConfiguration;
import com.xwc.open.easybatis.supports.DefaultParamArgsResolver;
import com.xwc.open.easybatis.supports.DriverDatabaseIdProvider;
import com.xwc.open.easybatis.supports.SqlSourceGenerator;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/9 19:06
 */

public class MySqlConfigurationCustomizer implements ConfigurationCustomizer {

    private final SqlSourceGenerator sqlSourceGenerator;

    public MySqlConfigurationCustomizer(SqlSourceGenerator sqlSourceGenerator) {
        this.sqlSourceGenerator = sqlSourceGenerator;
    }

    @Override
    public void customize(Configuration configuration) {
        if (configuration instanceof EasyBatisConfiguration) {
            EasyBatisConfiguration easyBatisConfiguration = (EasyBatisConfiguration) configuration;
            easyBatisConfiguration.registrySqlSourceGenerator(DriverDatabaseIdProvider.MYSQL,
                    sqlSourceGenerator, new DefaultParamArgsResolver(easyBatisConfiguration));
            easyBatisConfiguration.setDatabaseId(DriverDatabaseIdProvider.MYSQL);
        }
    }
}
