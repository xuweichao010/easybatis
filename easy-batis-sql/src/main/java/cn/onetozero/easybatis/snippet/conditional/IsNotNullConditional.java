package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.annotaions.conditions.IsNull;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class IsNotNullConditional implements SingleConditionalSnippet {


    private final AbstractBatisSourceGenerator sourceGenerator;

    public IsNotNullConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        IsNull equal = columnAttribute.findAnnotation(IsNull.class);
        String conditionSql = "AND " + sqlPlaceholder.holder(columnAttribute.useColumn(equal)) + " IS NOT NULL ";
        if (columnAttribute.useDynamic(equal)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}