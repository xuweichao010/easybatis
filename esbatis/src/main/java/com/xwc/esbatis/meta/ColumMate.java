package com.xwc.esbatis.meta;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/13  17:16
 * 业务：
 * 功能：属性
 */
public class ColumMate {
    private String field;
    private String colunm;


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getColunm() {
        return colunm;
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
        return sb.append("#{").append(item+"."+field).append("}");
    }

    public StringBuilder getBatisColum() {
        StringBuilder sb = new StringBuilder();
        return sb.append("${").append(field).append("}");
    }
}
