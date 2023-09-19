package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.annotaions.conditions.GreaterThanEqual;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class GreaterThanEqualConditional implements SingleConditionalSnippet {

    private final AbstractBatisSourceGenerator sourceGenerator;

    public GreaterThanEqualConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        GreaterThanEqual equal = columnAttribute.findAnnotation(GreaterThanEqual.class);
        String conditionSql = "AND " +columnAttribute.useAlias(equal) +sqlPlaceholder.holder(columnAttribute.useColumn(equal))
                + " <![CDATA[>=]]> " + batisPlaceholder.holder(columnAttribute);
        if (columnAttribute.useDynamic(equal)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
