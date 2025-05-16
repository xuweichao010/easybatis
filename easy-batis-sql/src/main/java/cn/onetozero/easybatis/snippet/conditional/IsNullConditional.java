package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easy.annotations.conditions.IsNull;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：等值SQL片段
 * @author  徐卫超 (cc)
 * @since 2023/1/17 13:51
 */
public class IsNullConditional implements SingleConditionalSnippet {


    private final AbstractBatisSourceGenerator sourceGenerator;

    public IsNullConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        IsNull equal = columnAttribute.findAnnotation(IsNull.class);
        String conditionSql = "AND " + sqlPlaceholder.holder(columnAttribute.useColumn(equal)) + " IS NULL ";
        if (columnAttribute.useDynamic(equal)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
