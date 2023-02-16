package cn.onetozero.easybatis.spring.boot.autoconfigure.mysql;

import cn.onetozero.easy.parse.utils.StringUtils;
import cn.onetozero.easybatis.mysql.MysqlSqlSourceGenerator;
import cn.onetozero.easybatis.supports.DefaultParamArgsResolver;
import cn.onetozero.easybatis.supports.DriverDatabaseIdProvider;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

/**
 * 类描述：配置config 并注册
 * 作者：徐卫超 (cc)
 * 时间 2023/2/9 19:06
 */

public class MySqlConfigurationCustomizer implements ConfigurationCustomizer {

    private final MysqlSqlSourceGenerator sqlSourceGenerator;

    public MySqlConfigurationCustomizer(MysqlSqlSourceGenerator sqlSourceGenerator) {
        this.sqlSourceGenerator = sqlSourceGenerator;
    }

    @Override
    public void customize(Configuration configuration) {
        if (configuration instanceof EasyBatisConfiguration) {
            EasyBatisConfiguration easyBatisConfiguration = (EasyBatisConfiguration) configuration;
            easyBatisConfiguration.registrySqlSourceGenerator(DriverDatabaseIdProvider.MYSQL,
                    sqlSourceGenerator, new DefaultParamArgsResolver(easyBatisConfiguration));
            if (StringUtils.hasText(configuration.getDatabaseId())) {
                easyBatisConfiguration.setDatabaseId(DriverDatabaseIdProvider.MYSQL);
            }
        }
    }
}
