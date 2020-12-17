package com.xwc.open.easybatis.core.support.table;

/**
 * 作者：徐卫超 cc
 * 时间：2020/9/9
 * 描述：逻辑列
 */
public class LoglicColumn extends ColumnMeta {
    private int valid;

    private int invalid;

    public LoglicColumn(ColumnMeta columnMeta, int valid, int invalid) {
        super(columnMeta);
        this.valid = valid;
        this.invalid = invalid;
    }

    public int getValid() {
        return valid;
    }

    public int getInvalid() {
        return invalid;
    }
}
