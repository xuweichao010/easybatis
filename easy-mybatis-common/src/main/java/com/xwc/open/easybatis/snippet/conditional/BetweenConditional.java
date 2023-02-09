package com.xwc.open.easybatis.snippet.conditional;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.conditions.Between;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.AbstractBatisSourceGenerator;
import com.xwc.open.easybatis.supports.BatisPlaceholder;
import com.xwc.open.easybatis.supports.SqlPlaceholder;

/**
 * 类描述： Between SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class BetweenConditional implements MultiConditionalSnippet {

    private final AbstractBatisSourceGenerator sourceGenerator;

    public BetweenConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute fromAttribute, BatisColumnAttribute toAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        Between between = fromAttribute.findAnnotation(Between.class);
        String conditionSql = "AND " + sqlPlaceholder.holder(fromAttribute.useColumn(between)) + " BETWEEN " + batisPlaceholder.holder(fromAttribute) +
                " AND " + batisPlaceholder.holder(toAttribute);
        if (fromAttribute.isMethodDynamic() || between.dynamic()) {
            return MyBatisSnippetUtils.ifNonCondition(batisPlaceholder.path(fromAttribute), batisPlaceholder.path(toAttribute), conditionSql);
        } else {
            return conditionSql;
        }
    }
}
