package com.xwc.open.easybatis.snippet.column;

import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.AbstractBatisSourceGenerator;
import com.xwc.open.easybatis.supports.SqlPlaceholder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 14:07
 */
public class DefaultInsertColumn implements InsertColumnSnippet {
    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultInsertColumn(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String columns(List<BatisColumnAttribute> columnAttribute) {
        SqlPlaceholder sqlPlaceholder = sourceGenerator.getSqlPlaceholder();
        return "(" + columnAttribute.stream()
                .map(BatisColumnAttribute::getColumn)
                .map(sqlPlaceholder::holder)
                .collect(Collectors.joining(",")) + ")";
    }
}
