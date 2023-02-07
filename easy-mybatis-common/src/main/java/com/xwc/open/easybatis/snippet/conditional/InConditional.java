package com.xwc.open.easybatis.snippet.conditional;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.In;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;
import com.xwc.open.easybatis.supports.ColumnPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class InConditional implements SingleConditionalSnippet {

    private BatisPlaceholder placeholder;

    private ColumnPlaceholder columnPlaceholder;

    public InConditional(BatisPlaceholder placeholder, ColumnPlaceholder columnPlaceholder) {
        this.placeholder = placeholder;
        this.columnPlaceholder = columnPlaceholder;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        In In = columnAttribute.findAnnotation(In.class);
        String path = columnAttribute.isMulti() || columnAttribute.getPath().length > 1 ?
                placeholder.path(columnAttribute) : "collection";
        String conditionSql =
                "AND " + columnPlaceholder.holder(columnAttribute.useColumn(In)) + " IN " + MyBatisSnippetUtils.foreachItem("item", path);
        if (columnAttribute.useDynamic(In)) {
            return MyBatisSnippetUtils.ifNonNullObject(placeholder.path(columnAttribute), conditionSql);
        } else {
            return conditionSql;
        }
    }
}
