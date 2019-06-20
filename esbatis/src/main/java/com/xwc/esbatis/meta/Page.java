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
    private Long start = 1L;
    @LimitOffset
    private Long offset = 10L;


    public Long getPageNum() {
        return start;
    }

    public void setPageNum(Long pageNum) {
        this.start = pageNum;
    }

    public Long getPageSize() {
        return offset;
    }

    public void setPageSize(Long pageSize) {
        this.offset = pageSize;
    }

    public Long getStart() {
        return (start - 1) * offset;
    }

    public Long getOffset() {
        return offset;
    }
}
