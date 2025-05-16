package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easy.annotations.conditions.Like;
import cn.onetozero.easy.annotations.conditions.Likes;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.exceptions.ParamCheckException;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述： 多列模糊查询SQL片段
 *
 * @author 徐卫超 (cc)
 * @since 2023/1/17 13:51
 */
public class LikesConditional implements SingleConditionalSnippet {

    private final AbstractBatisSourceGenerator sourceGenerator;

    public LikesConditional(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String snippet(BatisColumnAttribute columnAttribute) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
        Likes likes = columnAttribute.findAnnotation(Likes.class);
        if (likes.value() == null || likes.value().length == 0) {
            throw new ParamCheckException("在 " + columnAttribute.getParameterName() + "上的 Likes 注解的value 不能为空");
        }
        String likesSql = Stream.of(likes.value())
                .map(sqlPlaceholder::holder)
                .map(column -> column + " LIKE CONCAT('%'," + batisPlaceholder.holder(columnAttribute) + ",'%') ")
                .collect(Collectors.joining(" OR "));

        String conditionSql = "AND ( " + likesSql + " )";
        if (columnAttribute.useDynamic(likes)) {
            return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(columnAttribute), conditionSql);
        } else {
            return conditionSql;
        }
    }
}
