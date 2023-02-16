package cn.onetozero.easy.parse.model;

import cn.onetozero.easy.parse.enums.FillType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:16
 */
@SuppressWarnings("unused")
public class TableMeta {

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
    private final List<ModelAttribute> normalAttr = new ArrayList<>();
    /**
     * 填充字段信息
     */
    private final List<FillAttribute> fills = new ArrayList<>();
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


    public List<FillAttribute> getFills() {
        return fills;
    }

    public List<FillAttribute> updateFillAttributes() {
        return fills.stream().filter(fillAttribute -> fillAttribute.getType() == FillType.UPDATE
                || fillAttribute.getType() == FillType.INSERT_UPDATE).collect(Collectors.toList());
    }

    public List<FillAttribute> insertFillAttributes() {
        return fills.stream().filter(fillAttribute -> fillAttribute.getType() == FillType.INSERT
                || fillAttribute.getType() == FillType.INSERT_UPDATE).collect(Collectors.toList());
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

    public void addNormalAttribute(ModelAttribute modelAttribute) {
        this.normalAttr.add(modelAttribute);
    }

    public List<ModelAttribute> getNormalAttr() {
        return normalAttr;
    }
}
