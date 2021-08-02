
package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.snippet.InsertColumnSnippet;

import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：
 */
public class MySqlInsertColumnSnippet implements InsertColumnSnippet {

    private final PlaceholderBuilder placeholderBuilder;

    public MySqlInsertColumnSnippet(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

    @Override
    public String apply(MethodMeta methodMeta) {
        return " (" + MysqlCommonsUtils.column(methodMeta.getTableMetadata()).stream()
                .map(field -> placeholderBuilder.columnHolder(null, field.getColumn()).getHolder())
                .collect(Collectors.joining(", ")) + ")";
    }
}
