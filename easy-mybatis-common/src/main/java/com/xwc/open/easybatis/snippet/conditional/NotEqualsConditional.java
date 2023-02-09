package com.xwc.open.easybatis.snippet.conditional;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.Equal;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.AbstractBatisSourceGenerator;
import com.xwc.open.easybatis.supports.BatisPlaceholder;
import com.xwc.open.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class NotEqualsConditional implements SingleConditionalSnippet {


    private final AbstractBatisSourceGenerator sourceGenerator;

    public NotEqualsConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        Equal equal = columnAttribute.findAnnotation(Equal.class);
        String conditionSql = "AND " + sqlPlaceholder.holder(columnAttribute.useColumn(equal))
                + " != " + batisPlaceholder.holder(columnAttribute);
        if (columnAttribute.useDynamic(equal)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
