package com.xwc.open.easybatis.core.mysql;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.mysql.snippet.MySqlConditionSnippet;
import com.xwc.open.easybatis.core.mysql.snippet.MySqlFromSnippet;
import com.xwc.open.easybatis.core.mysql.snippet.MySqlOrderBySnippet;
import com.xwc.open.easybatis.core.mysql.snippet.MySqlSelectColumnSnippet;
import com.xwc.open.easybatis.core.support.AbstractSqlSourceGenerator;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:33
 * 备注：
 */
public class MysqlSqlSourceGenerator extends AbstractSqlSourceGenerator {

    public MysqlSqlSourceGenerator(PlaceholderBuilder placeholderBuilder) {
        super(placeholderBuilder);
        this.selectColumnSnippet = new MySqlSelectColumnSnippet(placeholderBuilder);
        this.fromSnippet = new MySqlFromSnippet();
        this.conditionSnippet = new MySqlConditionSnippet(placeholderBuilder);
        this.orderBySnippet = new MySqlOrderBySnippet(placeholderBuilder);
//        this.deleteConditionSnippet = new MySqlDeleteConditionSnippet(selectConditionSnippet);
//        this.insertColumnValue = new DefaultInsertValueSnippet();
//        this.updateColumnSnippet = new DefaultUpdateColumnSnippet();
//        this.updateConditionSnippet = new DefaultUpdateConditionSnippet(this.selectConditionSnippet);

//        this.pageSnippet = new DefaultPageSnippet();
//        this.deleteLogicSnippet = new MySqlDeleteLogicSnippet();
    }

    @Override
    public String select(MethodMeta methodMetaData) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>")
                .append(" SELECT ").append(this.selectColumnSnippet.apply(methodMetaData))
                .append(" FROM ").append(this.fromSnippet.apply(methodMetaData));
        String conditionSnippet = this.conditionSnippet.apply(methodMetaData);
        if (StringUtils.hasText(conditionSnippet)) {
            sb.append(" <where>").append(conditionSnippet).append(" </where>");
        }
        String orderSnippet = this.orderBySnippet.apply(methodMetaData);
        if (StringUtils.hasText(orderSnippet)) {
            sb.append(" <trim prefix='ORDER BY' suffixOverrides=','> ").append(orderSnippet).append(" </trim>");
        }
//        String page = this.pageSnippet.apply(methodMetaData);
//        if (StringUtils.hasText(page)) {
//            sb.append(" LIMIT ").append(page);
//        }
        sb.append("</script>");
        return sb.toString();
    }

    @Override
    public String insert(MethodMeta methodMetaData) {
        StringBuilder sql = new StringBuilder();
//        sql.append("<script>")
//                .append(" INSERT INTO ")
//                .append(methodMetaData.getTableMetadata().getTableName())
//                .append(" (").append(this.insertColumn(methodMetaData)).append(")")
//                .append(" VALUES ")
//                .append(this.insertColumnValue(methodMetaData));
//        sql.append(" </script>");
        return sql.toString();
    }


    @Override
    public String update(MethodMeta methodMetaData) {
        StringBuilder sb = new StringBuilder();
//        sb.append("<script>")
//                .append(" UPDATE ").append(methodMetaData.getTableMetadata().getTableName());
//        if (methodMetaData.hasDynamic()) {
//            sb.append(" <set>").append(this.updateColumnSnippet.apply(methodMetaData)).append(" </set>");
//        } else {
//            sb.append(" SET ").append(this.updateColumnSnippet.apply(methodMetaData));
//        }
//        String conditionSnippet = this.updateConditionSnippet.apply(methodMetaData);
//        if (StringUtils.hasText(conditionSnippet)) {
//            sb.append(" <where>").append(conditionSnippet).append(" </where>");
//        }
//        sb.append("</script>");
        return sb.toString();
    }

    @Override
    public String delete(MethodMeta methodMetaData) {
        // 处理逻辑删除的情况
        if (methodMetaData.getTableMetadata().getLogic() == null) {
            return doDelete(methodMetaData);
        } else {
            return doLogicDelete(methodMetaData);
        }
    }

    private String doLogicDelete(MethodMeta methodMetaData) {
        StringBuilder sb = new StringBuilder();
//        sb.append("<script>")
//                .append(" UPDATE ").append(methodMetaData.getTableMetadata().getTableName())
//                .append(" SET ").append(this.deleteLogicSnippet.apply(methodMetaData));
//        String conditionSnippet = this.deleteConditionSnippet.apply(methodMetaData);
//        if (StringUtils.hasText(conditionSnippet)) {
//            sb.append(" <where>").append(conditionSnippet).append(" </where>");
//        }
//        sb.append("</script>");
        return sb.toString();
    }

    private String doDelete(MethodMeta methodMetaData) {
        StringBuilder sb = new StringBuilder();
//        sb.append("<script> DELETE FROM ").append(methodMetaData.getTableMetadata().getTableName());
//        String conditionSnippet = this.deleteConditionSnippet.apply(methodMetaData);
//        if (StringUtils.hasText(conditionSnippet)) {
//            sb.append(" <where>").append(conditionSnippet).append(" </where>");
//        }
//        sb.append("</script>");
        return sb.toString();
    }
}
