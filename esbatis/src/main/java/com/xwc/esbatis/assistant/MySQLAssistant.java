package com.xwc.esbatis.assistant;

import com.xwc.esbatis.anno.Count;
import com.xwc.esbatis.anno.Distinct;
import com.xwc.esbatis.interfaces.SqlAssistant;
import com.xwc.esbatis.meta.ColumMate;
import com.xwc.esbatis.meta.FilterColumMate;
import org.apache.ibatis.binding.BindingException;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/8  16:11
 * 业务：
 * 功能：
 */
public class MySQLAssistant implements SqlAssistant {


    /**
     * 构建汇总片段
     */
    @Override
    public StringBuilder builderCountOrDistinct(StringBuilder colums, Count count, Distinct distinct) {
        StringBuilder sb = new StringBuilder();
        if (distinct != null && count == null) {
            return sb.append(" DISTINCT ").append(colums);
        } else if (count != null && distinct == null) {
            return sb.append(" COUNT(*) ");
        } else if (count != null) {
            return sb.append(" COUNT( DISTINCT ").append(colums).append(" )");
        } else {
            return colums;
        }
    }

    /**
     * 构建查询属性片段
     */
    @Override
    public StringBuilder builderColum(List<ColumMate> list, String annoCloums) {
        StringBuilder sb = new StringBuilder();
        if (!annoCloums.trim().isEmpty()) {
            sb.append(annoCloums);
        } else {
            list.forEach(item -> sb.append(", ").append(item.getColunm()));
            sb.delete(0, 1);
        }
        return sb;
    }

    /**
     * 构建一个修改属性片段
     */
    public StringBuilder builderSet(List<ColumMate> set) {
        StringBuilder sb = new StringBuilder();
        set.forEach(item -> sb.append(", ").append(item.getColunm()).append("=").append(item.getBatisField()));
        return sb.delete(0, 1);
    }

    /**
     * 构建插入数据片段
     */
    @Override
    public StringBuilder builderInsert(List<ColumMate> list) {
        StringBuilder colunm = new StringBuilder();
        StringBuilder field = new StringBuilder();
        list.forEach(item -> {
            colunm.append(", ").append(item.getColunm());
            field.append(", ").append(item.getBatisField());
        });
        StringBuilder sb = new StringBuilder();
        sb.append(" ( ").append(colunm.delete(0, 1))
                .append(" ) VALUES ( ")
                .append(field.delete(0, 1).append(" )"));
        return sb;
    }

    @Override
    public StringBuilder builderInsertBatch(List<ColumMate> list) {
        StringBuilder colunm = new StringBuilder();
        StringBuilder field = new StringBuilder();
        list.forEach(item -> {
            colunm.append(", ").append(item.getColunm());
            field.append(", ").append(item.getBatisField("item"));
        });
        StringBuilder sb = new StringBuilder();
        sb.append(" ( ").append(colunm.substring(1))
                .append(") VALUES <foreach collection='list' item='item' index='index' separator=',' >")
                .append(field.delete(0, 1).insert(0, "(").append(")"))
                .append("</foreach>");
        return sb;
    }

    /**
     * 构建一个动态查询条件片段
     */
    @Override
    public StringBuilder builderDynamicQuery(List<FilterColumMate> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(" <where> ");
        list.forEach(item -> {
            switch (item.getConditionEnum()) {
                case IN:
                case NOT_IN:
                    sb.append(String.format(TMPLATE_IF, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField())));
                    break;
                case EQUEL:
                case NOT_EQUEL:
                case LIKE:
                case LEFT_LIKE:
                case RIGHT_LIKE:
                case NOT_LIKE:
                case NOT_LEFT_LIKE:
                case NOT_RIGHT_LIKE:
                    sb.append(String.format(TMPLATE_IF, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField())));
                    break;
                case IS_NULL:
                case NOT_NULL:
                    sb.append(String.format(TMPLATE_IF, item.getField(), String.format(item.getConditionEnum().getSymbol(), item.getColunm())));
                    break;
                case GROUP_BY:
                case OEDER_ASC:
                case OEDER_DESC:
                case LIMIT_START:
                case LIMIT_OFFSET:
                default:
                    throw new BindingException("生成sql语句约束错误");
            }
        });
        sb.append(" </where>");
        return sb;
    }

    @Override
    public StringBuilder builderQuery(List<FilterColumMate> list) {
        StringBuilder sb = new StringBuilder();
        if (list.isEmpty()) return sb;
        list.forEach(item -> {
            switch (item.getConditionEnum()) {
                case IN:
                case NOT_IN:
                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getField()));
                    break;
                case EQUEL:
                case NOT_EQUEL:
                case LIKE:
                case LEFT_LIKE:
                case RIGHT_LIKE:
                case NOT_LIKE:
                case NOT_LEFT_LIKE:
                case NOT_RIGHT_LIKE:
                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm(), item.getBatisField()));
                    break;
                case IS_NULL:
                case NOT_NULL:
                    sb.append(String.format(item.getConditionEnum().getSymbol(), item.getColunm()));
                    break;
                default:
                    throw new BindingException("生成sql语句约束错误");
            }
        });
        sb.delete(0, 4).insert(0, " WHERE ");
        return sb;
    }

    @Override
    public StringBuilder builderOrderBy(FilterColumMate mate) {
        StringBuilder sb = new StringBuilder();
        if (mate == null) return sb;
        sb.append(" ORDER BY ").append(mate.getFieldValue());
        return sb;
    }

    @Override
    public StringBuilder builderGroupBy(FilterColumMate mate) {
        StringBuilder sb = new StringBuilder();
        if (mate == null) return sb;
        sb.append(" GROUP BY ").append(mate.getFieldValue());
        return sb;
    }

    @Override
    public StringBuilder builderPage(FilterColumMate start, FilterColumMate offset) {
        StringBuilder sb = new StringBuilder();
        if (start == null || offset == null) return sb;
        sb.append(" LIMIT ").append(start.getBatisField()).append(",").append(offset.getBatisField());
        return sb;
    }


}
