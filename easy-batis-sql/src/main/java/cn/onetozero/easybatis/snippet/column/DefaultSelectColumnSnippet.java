package cn.onetozero.easybatis.snippet.column;

import cn.onetozero.easy.parse.model.ModelAttribute;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.utils.StringUtils;
import cn.onetozero.easy.annotations.SelectSql;
import cn.onetozero.easy.annotations.other.Count;
import cn.onetozero.easy.annotations.other.Distinct;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.SqlPlaceholder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/25 20:21
 */
public class DefaultSelectColumnSnippet implements SelectColumnSnippet {
    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultSelectColumnSnippet(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String columns(OperateMethodMeta operateMethodMeta, List<ModelAttribute> columnAttribute) {
        SelectSql selectSql = operateMethodMeta.findAnnotation(SelectSql.class);
        Count count = operateMethodMeta.findAnnotation(Count.class);
        String cSql = selectSql.value();
        if (StringUtils.hasText(selectSql.value())) {
            Distinct distinct = operateMethodMeta.findAnnotation(Distinct.class);
            if (distinct != null) {
                cSql = "DISTINCT(" + cSql + ")";
            }
            if (count != null) {
                cSql = "COUNT(" + cSql + ")";
            }
            return cSql;
        } else {
            if (count != null) {
                return " COUNT(*)";
            } else {
                SqlPlaceholder sqlPlaceholder = this.sourceGenerator.getSqlPlaceholder();
                return columnAttribute.stream().map(ModelAttribute::getColumn).map(sqlPlaceholder::holder).collect(Collectors.joining(","));
            }

        }

    }
}
