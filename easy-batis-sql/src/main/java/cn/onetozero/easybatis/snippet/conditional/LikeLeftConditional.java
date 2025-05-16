package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easy.annotations.conditions.LikeLeft;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：等值SQL片段
 * @author  徐卫超 (cc)
 * @since 2023/1/17 13:51
 */
public class LikeLeftConditional implements SingleConditionalSnippet {


    private final AbstractBatisSourceGenerator sourceGenerator;

    public LikeLeftConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }
    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        LikeLeft like = columnAttribute.findAnnotation(LikeLeft.class);
        String conditionSql = "AND " + sqlPlaceholder.holder(columnAttribute.useColumn(like))
                + " LIKE CONCAT('%'," + batisPlaceholder.holder(columnAttribute) + ") ";
        if (columnAttribute.useDynamic(like)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute),
                    conditionSql);
        } else {
            return conditionSql;
        }
    }
}
