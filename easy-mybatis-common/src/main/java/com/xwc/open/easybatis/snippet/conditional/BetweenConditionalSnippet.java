package com.xwc.open.easybatis.snippet.conditional;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.Between;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class BetweenConditionalSnippet implements MultiConditionalSnippet {

    private BatisPlaceholder placeholder;

    public BetweenConditionalSnippet(BatisPlaceholder placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String snippet(BatisColumnAttribute fromAttribute, BatisColumnAttribute toAttribute) {
        Between between = fromAttribute.findAnnotation(Between.class);
        String conditionSql = "AND " + fromAttribute.getColumn() + " BETWEEN " + placeholder.holder(fromAttribute) +
                " AND " + placeholder.holder(toAttribute);
        if (fromAttribute.isMethodDynamic() || between.dynamic()) {
            return MyBatisSnippetUtils.ifNonCondition(placeholder.path(fromAttribute), placeholder.path(toAttribute), conditionSql);
        } else {
            return conditionSql;
        }
    }
}
