package com.xwc.open.easybatis.dto;

import com.xwc.open.easybatis.annotaions.page.Limit;
import com.xwc.open.easybatis.annotaions.page.Offset;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 13:32
 */
public class PageQueryDto {

    @Limit
    private int limit = 10;

    @Offset
    private int offset = 0;

    public static PageQueryDto create(int pageNum, int pageSize) {
        PageQueryDto tar = new PageQueryDto();
        tar.setLimit(pageSize);
        tar.setOffset((pageNum - 1) * pageSize);
        return tar;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
