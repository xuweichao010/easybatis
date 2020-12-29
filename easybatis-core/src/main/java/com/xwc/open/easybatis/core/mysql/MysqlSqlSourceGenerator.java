package com.xwc.open.easybatis.core.mysql;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.interfaces.AbstractSqlSourceGenerator;
import com.xwc.open.easybatis.core.interfaces.snippet.DefaultInsertValueSnippet;
import com.xwc.open.easybatis.core.interfaces.snippet.DefaultUpdateColumnSnippet;
import com.xwc.open.easybatis.core.interfaces.snippet.DefaultUpdateConditionSnippet;
import com.xwc.open.easybatis.core.support.MethodMeta;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:33
 * 备注：
 */
public class MysqlSqlSourceGenerator extends AbstractSqlSourceGenerator {

    public MysqlSqlSourceGenerator() {
        this.insertColumnValue = new DefaultInsertValueSnippet();
        this.updateColumnSnippet = new DefaultUpdateColumnSnippet();
        this.updateConditionSnippet = new DefaultUpdateConditionSnippet(this.selectConditionSnippet);
    }

    @Override
    public String select(MethodMeta methodMetaData) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>")
                .append(" SELECT ").append(this.selectColumnSnippet.apply(methodMetaData))
                .append(" FROM ").append(methodMetaData.getTableMetadata().getTableName());
        String conditionSnippet = this.selectConditionSnippet.apply(methodMetaData);
        if (StringUtils.hasText(conditionSnippet)) {
            sb.append(" WHERE ").append(conditionSnippet);
        }
        sb.append("</script>");
        return sb.toString();
    }

    @Override
    public String insert(MethodMeta methodMetaData) {
        StringBuilder sql = new StringBuilder();
        sql.append("<script>")
                .append(" INSERT INTO ")
                .append(methodMetaData.getTableMetadata().getTableName())
                .append(" (").append(this.insertColumn(methodMetaData)).append(")")
                .append(" VALUES ")
                .append(this.insertColumnValue(methodMetaData));
        sql.append(" </script>");
        return sql.toString();
    }


    @Override
    public String update(MethodMeta methodMetaData) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>")
                .append(" UPDATE ").append(methodMetaData.getTableMetadata().getTableName());
        if (methodMetaData.hasDynamic()) {
            sb.append(" <set> ").append(this.updateColumnSnippet.apply(methodMetaData)).append(" </set>");
        } else {
            sb.append(" SET ").append(this.updateColumnSnippet.apply(methodMetaData));
        }
        if (methodMetaData.hashCondition()) {
            sb.append(" WHERE ").append(this.updateConditionSnippet.apply(methodMetaData));
        }
        sb.append("</script>");
        return sb.toString();
    }

    @Override
    public String delete(MethodMeta methodMetaData) {
        return null;
    }
}
