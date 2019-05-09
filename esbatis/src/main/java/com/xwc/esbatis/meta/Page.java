package com.xwc.esbatis.meta;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/25  9:31
 * 业务：
 * 功能：分页功能
 */
public class Page {
    private Integer size;
    private Integer num;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
