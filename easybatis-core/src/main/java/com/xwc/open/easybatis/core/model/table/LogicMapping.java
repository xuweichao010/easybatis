package com.xwc.open.easybatis.core.model.table;

import com.xwc.open.easybatis.core.anno.table.Logic;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;

/**
 * 作者：徐卫超 cc
 * 时间：2020/9/9
 * 描述：逻辑列
 */
public class LogicMapping extends Mapping {
    private int valid;

    private int invalid;

    public LogicMapping(Mapping columnMeta, Logic logic) {
        super(columnMeta);
        this.valid = logic.valid();
        this.invalid = logic.invalid();
        this.setUpdateIgnore(true);
        this.setInsertIgnore(false);
        this.setSelectIgnore(true);
        this.mergeTableAnnotation(AnnotationUtils.getAnnotationAttributes(logic));
    }

    public int getValid() {
        return valid;
    }

    public int getInvalid() {
        return invalid;
    }
}
