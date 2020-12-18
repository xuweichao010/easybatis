package com.xwc.open.easybatis.core.mysql;

import com.xwc.open.easybatis.core.interfaces.AbstractSqlSourceGenerator;
import com.xwc.open.easybatis.core.interfaces.condition.CompareCondition;
import com.xwc.open.easybatis.core.support.MethodMeta;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:33
 * 备注：
 */
public class MysqlSqlSourceGenerator extends AbstractSqlSourceGenerator {

    public MysqlSqlSourceGenerator() {
        this.list = Stream.of(new CompareCondition()).collect(Collectors.toList());
    }

    @Override
    public String select(MethodMeta methodMetaData) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>")
                .append(" SELECT ").append(this.selectColumn(methodMetaData))
                .append(" FROM ").append(methodMetaData.getTableMetadata().getTableName());
        if (!methodMetaData.getParamMetaList().isEmpty() || methodMetaData.getTableMetadata().getLogic() != null) {
            sb.append(" WHERE ").append(this.queryCondition(methodMetaData));
        }
        sb.append("</script>");

        return sb.toString();
    }

    @Override
    public String insert(MethodMeta methodMetaData) {
        return null;
    }

    @Override
    public String update(MethodMeta methodMetaData) {
        return null;
    }

    @Override
    public String delete(MethodMeta methodMetaData) {
        return null;
    }
}
