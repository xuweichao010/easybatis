package com.xwc.esbatis.meta;

import com.xwc.esbatis.anno.condition.enhance.LimitOffset;
import com.xwc.esbatis.anno.condition.enhance.LimitStart;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/25  9:31
 * 业务：
 * 功能：分页功能
 */
public class Page {
    @LimitStart
    private Integer start = 1;
    @LimitOffset
    private Integer offset = 10;


    public Integer getPageNum() {
        return start;
    }

    public void setPageNum(Integer pageNum) {
        this.start = pageNum;
    }

    public Integer getPageSize() {
        return offset;
    }

    public void setPageSize(Integer pageSize) {
        this.offset = pageSize;
    }

    public Integer getStart() {
        return (start - 1) * offset;
    }

    public Integer getOffset() {
        return offset;
    }
}
