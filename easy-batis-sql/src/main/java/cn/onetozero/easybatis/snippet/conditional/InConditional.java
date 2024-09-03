package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.annotaions.conditions.In;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;

/**
 * 类描述：等值SQL片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public class InConditional implements SingleConditionalSnippet {

    private final AbstractBatisSourceGenerator sourceGenerator;

    public InConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        In In = columnAttribute.findAnnotation(In.class);
        String path = columnAttribute.isMulti() || columnAttribute.getPath().length > 1 ?
                batisPlaceholder.path(columnAttribute) : "collection";
        String conditionSql =
                "AND "  +sqlPlaceholder.holder(columnAttribute.useColumn(In)) + " IN " + MyBatisSnippetUtils.foreachItem("item", path);
        if (columnAttribute.useDynamic(In)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute), conditionSql);
        } else {
            return conditionSql;
        }
    }
}
