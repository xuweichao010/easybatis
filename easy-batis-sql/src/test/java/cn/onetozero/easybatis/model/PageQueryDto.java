package cn.onetozero.easybatis.model;

import cn.onetozero.easy.annotations.page.Limit;
import cn.onetozero.easy.annotations.page.Offset;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/1 13:32
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
