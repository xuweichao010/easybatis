package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.annotaions.conditions.Between;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;

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
