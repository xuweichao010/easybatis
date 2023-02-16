/*
 *    Copyright 2015-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package cn.onetozero.easybatis.spring.boot.autoconfigure;

import cn.onetozero.easy.parse.enums.IdType;
import cn.onetozero.easybatis.EasyBatisConfiguration;
import org.apache.ibatis.session.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Primary;

/**
 * Configuration properties for MyBatis.
 *
 * @author Eddú MeléndezE
 * @author Kazuki Shimizu
 */
@ConfigurationProperties(prefix = CustomMybatisProperties.MYBATIS_PREFIX)
@Primary
public class EasyMybatisProperties extends CustomMybatisProperties {


    /**
     * 当 @link com.xwc.open.parse.annotations.@Id注解 中 type 属性等于IdType.GLOBAL 的时候就是使用这里配置的属性,
     * 当不配置这里的属性时候 在对象中 @link com.xwc.open.parse.annotations.@Id注解 中 type 属性等于IdType.GLOBAL,
     * 系统就会抛出异常 @link com.xwc.open.parse.exceptions.CheckResultModelException
     */
    private IdType globalIdType;

    /**
     * 使用实体名自动关联表名开关 当实体 autoTableName为 true的时候 @link com.xwc.open.parse.annotations.Table注解中的value 属性
     * 就不用填写，如果填写就以填写的属性为主
     */
    private boolean autoTableName = false;

    /**
     * 映射的表名前缀
     * 当autoTableName等true有效
     */
    private String useTableNamePrefix;

    @NestedConfigurationProperty
    private EasyBatisConfiguration configuration;

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(EasyBatisConfiguration configuration) {
        this.configuration = configuration;
    }

    public IdType getGlobalIdType() {
        return globalIdType;
    }

    public void setGlobalIdType(IdType globalIdType) {
        this.globalIdType = globalIdType;
    }

    public boolean isAutoTableName() {
        return autoTableName;
    }

    public void setAutoTableName(boolean autoTableName) {
        this.autoTableName = autoTableName;
    }

    public String getUseTableNamePrefix() {
        return useTableNamePrefix;
    }

    public void setUseTableNamePrefix(String useTableNamePrefix) {
        this.useTableNamePrefix = useTableNamePrefix;
    }
}
