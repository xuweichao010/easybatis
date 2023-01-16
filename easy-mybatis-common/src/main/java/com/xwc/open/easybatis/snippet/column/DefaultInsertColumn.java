package com.xwc.open.easybatis.snippet.column;

import com.xwc.open.easybatis.Placeholder;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.DefaultColumnPlaceholder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 14:07
 */
public class DefaultInsertColumn implements InsertColumnSnippet {
    Placeholder placeholder;

    public DefaultInsertColumn(DefaultColumnPlaceholder defaultColumnPlaceholder) {
        placeholder = defaultColumnPlaceholder;
    }

    @Override
    public String columns(List<BatisColumnAttribute> columnAttribute) {
        return "(" + columnAttribute.stream()
                .map(placeholder::holder)
                .collect(Collectors.joining(",")) + ")";
    }
}
