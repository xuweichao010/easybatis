package com.xwc.open.easy.core.model;

import java.util.*;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:16
 */
@SuppressWarnings("unused")
public class DatabaseMeta {

    /**
     * 返回模型对应的表名
     */
    private String tableName;

    /**
     * 主键信息
     */
    private PrimaryKeyAttribute primaryKey;

    /**
     * 普通属性信息
     */
    private final Set<ModelAttribute> model = new HashSet<>();
    /**
     * 填充字段信息
     */
    private final Set<FillAttribute> fills = new HashSet<>();
    /**
     * 逻辑字段
     */
    private LogicAttribute logic;
    /**
     * 原始信息
     */
    private Class<?> source;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public PrimaryKeyAttribute getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(PrimaryKeyAttribute primaryKey) {
        this.primaryKey = primaryKey;
    }


    public Set<FillAttribute> getFills() {
        return fills;
    }

    public LogicAttribute getLogic() {
        return logic;
    }

    public void setLogic(LogicAttribute logic) {
        this.logic = logic;
    }

    public Class<?> getSource() {
        return source;
    }

    public void setSource(Class<?> source) {
        this.source = source;
    }

    public void addFillAttribute(FillAttribute modelAttribute) {
        this.fills.add(modelAttribute);
    }

    public void addModelAttribute(ModelAttribute modelAttribute) {
        this.model.add(modelAttribute);
    }


}
