package com.xwc.esbatis.assistant;

import com.xwc.esbatis.anno.Count;
import com.xwc.esbatis.anno.Distinct;
import com.xwc.esbatis.anno.enums.ConditionEnum;
import com.xwc.esbatis.interfaces.SqlAssistant;
import com.xwc.esbatis.meta.ColumMate;
import com.xwc.esbatis.meta.EntityMate;
import com.xwc.esbatis.meta.FilterColumMate;
import com.xwc.esbatis.meta.QueryMate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/9  9:08
 * 业务：
 * 功能：
 */
public class SqlAnnotationBuilder {

    private SqlAssistant sqlAssistant;

    public SqlAnnotationBuilder(SqlAssistant sqlAssistant) {
        this.sqlAssistant = sqlAssistant;
    }

    public String selectOne(EntityMate mate, String annoColums) {
        List<FilterColumMate> filterList = Stream.of(mate.getKey())
                .map(columMate -> new FilterColumMate(columMate.getField(), columMate.getColunm(), ConditionEnum.EQUEL, 0))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder("<script> SELECT ");
        sb.append(sqlAssistant.builderColum(mate.getSelectColum(), annoColums))
                .append(" FROM ").append(mate.getTableName())
                .append(sqlAssistant.builderQuery(filterList, mate.getLogic()))
                .append(" </script>");
        return sb.toString();
    }


    public String insertBatch(EntityMate mate) {
        StringBuilder sb = new StringBuilder(" <script> INSERT INTO ")
                .append(mate.getTableName())
                .append(sqlAssistant.builderInsertBatch(mate.getInsertColum(), mate.getLogic()))
                .append(" </script>");
        return sb.toString();
    }

    public String insert(EntityMate mate) {
        StringBuilder sb = new StringBuilder(" <script> INSERT INTO ")
                .append(mate.getTableName())
                .append(sqlAssistant.builderInsert(mate.getInsertColum(), mate.getLogic()))
                .append(" </script>");
        return sb.toString();
    }

    public String update(EntityMate mate) {
        List<FilterColumMate> filterList = Stream.of(mate.getKey())
                .map(columMate -> new FilterColumMate(columMate.getField(), columMate.getColunm(), ConditionEnum.EQUEL, 0))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append(" <script> UPDATE ").append(mate.getTableName()).append(" SET ")
                .append(sqlAssistant.builderSet(mate.getUpdateColum()))
                .append(sqlAssistant.builderQuery(filterList, mate.getLogic()))
                .append(" </script>");
        return sb.toString();
    }

    public String update(QueryMate queryMate, EntityMate entityMate) {
        List<ColumMate> setField = queryMate.getSet().stream().map(item -> (ColumMate) item).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        sb.append("<script> UPDATE ").append(entityMate.getTableName()).append(" SET ")
                .append(sqlAssistant.builderSet(setField))
                .append(sqlAssistant.builderQuery(queryMate.getQueryFilter(), entityMate.getLogic())).append(" </script>");
        return sb.toString();
    }


    public String selectQuery(EntityMate mate, QueryMate query, String annoColums, Boolean isDynamic, Count count, Distinct distinct) {
        StringBuilder sb = new StringBuilder("<script> SELECT ");
        sb.append(sqlAssistant.builderCountOrDistinct(sqlAssistant.builderColum(mate.getSelectColum(), annoColums), count, distinct))
                .append(" FROM ").append(mate.getTableName());
        sb.append(isDynamic ? sqlAssistant.builderDynamicQuery(query.getQueryFilter(), mate.getLogic()) : sqlAssistant.builderQuery(query.getQueryFilter(), mate.getLogic()));
        sb.append(sqlAssistant.builderGroupBy(query.getGroup()))
                .append(sqlAssistant.builderOrderBy(query.getOrder()));
        if (count == null) sb.append(sqlAssistant.builderPage(query.getStart(), query.getOffset()));
        sb.append(" </script>");
        return sb.toString();
    }

    public String delete(EntityMate mate) {
        List<FilterColumMate> filterList = Stream.of(mate.getKey())
                .map(columMate -> new FilterColumMate(columMate.getField(), columMate.getColunm(), ConditionEnum.EQUEL, 0))
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        if (mate.isLogic()) {
            List<ColumMate> updateList = new ArrayList<>();
            updateList.addAll(mate.getUpdateAudit());
            sb.append("<script> UPDATE ").append(mate.getTableName()).append(" SET ")
                    .append(sqlAssistant.builderSet(updateList))
                    .append(sqlAssistant.builderSetLogic(mate.getLogic(), !updateList.isEmpty()))
                    .append(sqlAssistant.builderQuery(filterList, mate.getLogic()))
                    .append(" </script>");
        } else {
            sb.append("<script> DELETE FROM ").append(mate.getTableName())
                    .append(sqlAssistant.builderQuery(filterList, mate.getLogic()))
                    .append(" </script>");
        }
        return sb.toString();
    }

    public String delete(EntityMate mate, QueryMate query) {
        StringBuilder sb = new StringBuilder();
        if (mate.isLogic()) {
            List<ColumMate> updateList = new ArrayList<>();
            updateList.addAll(mate.getUpdateAudit());
            sb.append("<script> UPDATE ").append(mate.getTableName()).append(" SET ")
                    .append(sqlAssistant.builderSet(updateList))
                    .append(sqlAssistant.builderSetLogic(mate.getLogic(), !updateList.isEmpty()))
                    .append(sqlAssistant.builderQuery(query.getQueryFilter(), mate.getLogic()))
                    .append(" </script>");
        } else {
            sb.append("<script> DELETE FROM ").append(mate.getTableName())
                    .append(sqlAssistant.builderQuery(query.getQueryFilter(), mate.getLogic()))
                    .append(" </script>");
        }
        return sb.toString();
    }
}
