package com.xwc.open.easybatis.core.support.table;

import com.xwc.open.easybatis.core.anno.table.Loglic;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;

/**
 * 作者：徐卫超 cc
 * 时间：2020/9/9
 * 描述：逻辑列
 */
public class LoglicColumn extends ColumnMeta {
    private int valid;

    private int invalid;

    public LoglicColumn(ColumnMeta columnMeta, Loglic loglic) {
        super(columnMeta);
        this.valid = loglic.valid();
        this.invalid = loglic.invalid();
        this.setUpdateIgnore(true);
        this.setInsertIgnore(false);
        this.setSelectIgnore(true);
        this.mergeTableAnnotation(AnnotationUtils.getAnnotationAttributes(loglic));
    }

    public int getValid() {
        return valid;
    }

    public int getInvalid() {
        return invalid;
    }
}
