package cn.onetozero.easybatis.snippet.column;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.SqlPlaceholder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/16 14:07
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
