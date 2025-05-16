package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easy.annotations.conditions.Equal;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：等值SQL片段
 * @author  徐卫超 (cc)
 * @since 2023/1/17 13:51
 */
public class EqualConditional implements SingleConditionalSnippet {

    private final AbstractBatisSourceGenerator sourceGenerator;

    public EqualConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        Equal equal = columnAttribute.findAnnotation(Equal.class);
        String conditionSql =
                "AND " + columnAttribute.useAlias(equal) + sqlPlaceholder.holder(columnAttribute.useColumn(equal)) + " = " + batisPlaceholder.holder(columnAttribute);
        if (columnAttribute.useDynamic(equal)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
