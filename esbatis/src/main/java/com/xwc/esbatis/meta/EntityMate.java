package com.xwc.esbatis.meta;


import com.xwc.esbatis.anno.enums.KeyEnum;
import org.apache.ibatis.binding.BindingException;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/13  17:09
 * 业务：
 * 功能：记录实体的信息
 */
public class EntityMate {

    private ColumMate primaryKey;
    private List<ColumMate> defaultColum = new ArrayList<>(10);
    private List<ColumMate> ignoreColum = new ArrayList<>(3);
    private String tableName;
    private KeyEnum keyEnum;

    public KeyEnum getKeyEnum() {
        return keyEnum;
    }

    public void setKeyEnum(KeyEnum keyEnum) {
        this.keyEnum = keyEnum;
    }

    public EntityMate validate() {
        if (primaryKey == null) throw new BindingException("not find PrimaryKey");
        return this;
    }


    public List<ColumMate> getDefaultColum() {
        return defaultColum;
    }


    public List<ColumMate> getSelectColum() {
        ArrayList<ColumMate> columMates = new ArrayList<>(defaultColum);
        columMates.add(0, primaryKey);
        return columMates;
    }

    public List<ColumMate> getInsertColum() {
        ArrayList<ColumMate> columMates = new ArrayList<>(defaultColum);
        if (keyEnum != KeyEnum.AUTO) {
            columMates.add(0, primaryKey);
        }
        return columMates;
    }

    public List<ColumMate> getUpdateColum() {
        ArrayList<ColumMate> columMates = new ArrayList<>(defaultColum);
        if (keyEnum != KeyEnum.AUTO) {
            columMates.add(0, primaryKey);
        }
        return columMates;
    }

    public ColumMate getKey() {
        return primaryKey;
    }


    /**
     * 添加需要字段
     *
     * @param colum
     */
    public void addDefault(ColumMate colum) {
        defaultColum.add(colum);
    }

    /**
     * 添加忽略的字段
     *
     * @param colum
     */
    public void addIgnore(ColumMate colum) {
        ignoreColum.add(colum);
        return;
    }

    /**
     * 添加主键
     *
     * @param primaryKey
     */
    public void addPrimaryKey(ColumMate primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getTableName() {
        return ColumMate.GA + tableName + ColumMate.GA;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


}
