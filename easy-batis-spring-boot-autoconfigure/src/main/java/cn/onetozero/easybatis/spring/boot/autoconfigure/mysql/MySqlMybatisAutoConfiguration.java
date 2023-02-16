package cn.onetozero.easybatis.spring.boot.autoconfigure.mysql;

import cn.onetozero.easybatis.mysql.MysqlSqlSourceGenerator;
import cn.onetozero.easybatis.supports.SqlSourceGenerator;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import cn.onetozero.easybatis.spring.boot.autoconfigure.EasyMybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 类描述：创建关于mysql的自动配置类
 * 作者：徐卫超 (cc)
 * 时间 2023/2/9 15:06
 */
@org.springframework.context.annotation.Configuration
@AutoConfigureAfter({EasyMybatisAutoConfiguration.class})
@ConditionalOnClass(name = {"com.mysql.cj.jdbc.Driver", "om.mysql.jdbc.Driver"})
public class MySqlMybatisAutoConfiguration {

    private List<MysqlSqlSourceSqlSourceGeneratorCustomizer> sqlSourceSqlSourceGeneratorCustomizer;

    public MySqlMybatisAutoConfiguration(ObjectProvider<List<MysqlSqlSourceSqlSourceGeneratorCustomizer>> sqlSourceSqlSourceGeneratorObjectProvider) {
        this.sqlSourceSqlSourceGeneratorCustomizer = sqlSourceSqlSourceGeneratorObjectProvider.getIfAvailable();
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer(MysqlSqlSourceGenerator mysqlSourceGenerator) {
        return new MySqlConfigurationCustomizer(mysqlSourceGenerator);
    }

    @Bean
    @ConditionalOnMissingBean
    public SqlSourceGenerator mysqlSourceGenerator(EasyBatisConfiguration configuration) {
        MysqlSqlSourceGenerator mysqlSqlSourceGenerator = new MysqlSqlSourceGenerator(configuration);
        if (sqlSourceSqlSourceGeneratorCustomizer != null && !CollectionUtils.isEmpty(sqlSourceSqlSourceGeneratorCustomizer)) {
            for (MysqlSqlSourceSqlSourceGeneratorCustomizer customizer : sqlSourceSqlSourceGeneratorCustomizer) {
                customizer.customize(mysqlSqlSourceGenerator);
            }
        }
        return mysqlSqlSourceGenerator;
    }


}
