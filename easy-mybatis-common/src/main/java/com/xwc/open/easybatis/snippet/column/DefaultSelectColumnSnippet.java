package com.xwc.open.easybatis.snippet.column;

import com.xwc.open.easy.parse.model.ModelAttribute;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.utils.StringUtils;
import com.xwc.open.easybatis.annotaions.SelectSql;
import com.xwc.open.easybatis.annotaions.other.Count;
import com.xwc.open.easybatis.annotaions.other.Distinct;
import com.xwc.open.easybatis.supports.ColumnPlaceholder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/25 20:21
 */
public class DefaultSelectColumnSnippet implements SelectColumnSnippet {
    ColumnPlaceholder placeholder;

    public DefaultSelectColumnSnippet(ColumnPlaceholder placeholder) {
        this.placeholder = placeholder;
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
                return columnAttribute.stream().map(ModelAttribute::getColumn).map(placeholder::holder).collect(Collectors.joining(","));
            }

        }

    }
}
