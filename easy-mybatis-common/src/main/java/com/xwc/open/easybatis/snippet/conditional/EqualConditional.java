package com.xwc.open.easybatis.snippet.conditional;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;
import com.xwc.open.easybatis.supports.ColumnPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class EqualConditional implements SingleConditionalSnippet {

    private BatisPlaceholder placeholder;

    private ColumnPlaceholder columnPlaceholder;

    public EqualConditional(BatisPlaceholder placeholder, ColumnPlaceholder columnPlaceholder) {
        this.placeholder = placeholder;
        this.columnPlaceholder = columnPlaceholder;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        Equal equal = columnAttribute.findAnnotation(Equal.class);
        String conditionSql = "AND " + columnPlaceholder.holder(columnAttribute.useColumn(equal)) + " = " + placeholder.holder(columnAttribute);
        if (columnAttribute.useDynamic(equal)) {
            return MyBatisSnippetUtils.ifNonNullObject(placeholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
