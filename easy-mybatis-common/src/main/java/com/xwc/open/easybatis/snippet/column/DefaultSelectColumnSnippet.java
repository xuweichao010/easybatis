package com.xwc.open.easybatis.snippet.column;

import com.xwc.open.easy.parse.model.ModelAttribute;
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
    public String columns(List<ModelAttribute> columnAttribute) {
        return columnAttribute.stream().map(ModelAttribute::getColumn).map(placeholder::holder).collect(Collectors.joining(","));
    }
}
