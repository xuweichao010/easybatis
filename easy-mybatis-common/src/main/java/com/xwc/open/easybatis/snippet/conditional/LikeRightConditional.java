package com.xwc.open.easybatis.snippet.conditional;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.Like;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;
import com.xwc.open.easybatis.supports.ColumnPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class LikeRightConditional implements SingleConditionalSnippet {

    private BatisPlaceholder placeholder;

    private ColumnPlaceholder columnPlaceholder;

    public LikeRightConditional(BatisPlaceholder placeholder, ColumnPlaceholder columnPlaceholder) {
        this.placeholder = placeholder;
        this.columnPlaceholder = columnPlaceholder;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        Like like = columnAttribute.findAnnotation(Like.class);
        String conditionSql = "AND " + columnPlaceholder.holder(columnAttribute.useColumn(like))
                + " LIKE CONCAT(" + placeholder.holder(columnAttribute) + ",'%') ";
        if (columnAttribute.useDynamic(like)) {
            return MyBatisSnippetUtils.ifNonNullObject(placeholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
