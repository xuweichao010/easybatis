package com.xwc.open.easybatis.snippet.conditional;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class EqualsConditionalSnippet implements SingleConditionalSnippet {

    private BatisPlaceholder placeholder;

    public EqualsConditionalSnippet(BatisPlaceholder placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        Equal equal = columnAttribute.findAnnotation(Equal.class);
        String conditionSql = "AND " + columnAttribute.getColumn() + " = " + placeholder.holder(columnAttribute);
        if (columnAttribute.isMethodDynamic() || (equal != null && equal.dynamic())) {
            return MyBatisSnippetUtils.ifNonNullObject(placeholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
