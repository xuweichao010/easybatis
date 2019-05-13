package com.xwc.esbatis.meta;

import com.xwc.esbatis.anno.enums.KeyEnum;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/13  17:16
 * 业务：
 * 功能：属性
 */
public class ColumMate {
    public static final String GA = "`";
    private String field;
    private String colunm;
    private KeyEnum keyEnum;
    private boolean isIgnore = false;

    public ColumMate(String field, String colunm) {
        this.field = field;
        this.colunm = colunm;
    }

    public ColumMate() {
    }

    public void setKeyEnum(KeyEnum keyEnum) {
        this.keyEnum = keyEnum;
    }

    public KeyEnum getKeyEnum() {
        return keyEnum;
    }

    public boolean isIgnore() {
        return isIgnore;
    }

    public void setIgnore(boolean ignore) {
        isIgnore = ignore;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getColunm() {
        if(colunm.startsWith(GA)){
            return colunm;
        }else {
            return GA + colunm + GA;
        }
    }

    public void setColunm(String colunm) {
        this.colunm = colunm;
    }

    public StringBuilder getBatisField() {
        StringBuilder sb = new StringBuilder();
        return sb.append("#{").append(field).append("}");
    }

    public StringBuilder getBatisField(String item) {
        StringBuilder sb = new StringBuilder();
        return sb.append("#{").append(item + "." + field).append("}");
    }

    public StringBuilder getFieldValue() {
        StringBuilder sb = new StringBuilder();
        return sb.append("${").append(field).append("}");
    }
}
