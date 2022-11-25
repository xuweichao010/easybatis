package com.xwc.open.easy.core;

import com.xwc.open.easy.core.enums.IdType;
import com.xwc.open.easy.core.assistant.DataBaseModelAssistant;
import com.xwc.open.easy.core.supports.IdGenerateHandlerFactory;
import com.xwc.open.easy.core.supports.NameConverter;
import com.xwc.open.easy.core.supports.impl.CamelConverterUnderscore;
import com.xwc.open.easy.core.assistant.DefaultDataBaseModelAssistant;
import com.xwc.open.easy.core.supports.impl.DefaultIdGenerateHandlerFactory;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:22
 */
public class EasyConfiguration {

    /**
     * 当 @link com.xwc.open.parse.annotations.@Id注解 中 type 属性等于IdType.GLOBAL 的时候就是使用这里配置的属性,
     * 当不配置这里的属性时候 在对象中 @link com.xwc.open.parse.annotations.@Id注解 中 type 属性等于IdType.GLOBAL,
     * 系统就会抛出异常 @link com.xwc.open.parse.exceptions.CheckResultModelException
     */
    private IdType globalIdType;

    /**
     * 以实体名俩自动创建表名 当实体 autoTableName为 true的时候 @link com.xwc.open.parse.annotations.Table注解中的value 属性
     * 就不用填写，如果填写就以填写的属性为主
     */
    private boolean autoTableName = false;

    /**
     * 使用表名前
     * 当autoTableName等true有效
     */
    private String useTableNamePrefix;

    /**
     * 属性名称转换器 默认使用驼峰转下划线
     */
    private NameConverter columnNameConverter = new CamelConverterUnderscore();

    /**
     * 表名称转换以 用于把类名装换称所需要的表名
     */
    private NameConverter tableNameConverter = new CamelConverterUnderscore();

    /**
     * 主键生成策略工厂 用于管理自定义主键创建策略
     */
    private IdGenerateHandlerFactory idGenerateHandlerFactory = new DefaultIdGenerateHandlerFactory();

    /**
     * 数据模型解析助手
     */
    private DataBaseModelAssistant baseModelAssistant = new DefaultDataBaseModelAssistant(this);


    public IdGenerateHandlerFactory getIdGenerateHandlerFactory() {
        return idGenerateHandlerFactory;
    }

    public void setIdGenerateHandlerFactory(IdGenerateHandlerFactory idGenerateHandlerFactory) {
        this.idGenerateHandlerFactory = idGenerateHandlerFactory;
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

    public NameConverter getColumnNameConverter() {
        return columnNameConverter;
    }

    public void setColumnNameConverter(NameConverter columnNameConverter) {
        this.columnNameConverter = columnNameConverter;
    }

    public NameConverter getTableNameConverter() {
        return tableNameConverter;
    }

    public void setTableNameConverter(NameConverter tableNameConverter) {
        this.tableNameConverter = tableNameConverter;
    }

    public DataBaseModelAssistant getBaseModelAssistant() {
        return baseModelAssistant;
    }

    public void setBaseModelAssistant(DataBaseModelAssistant baseModelAssistant) {
        this.baseModelAssistant = baseModelAssistant;
    }
}
