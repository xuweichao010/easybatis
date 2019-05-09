package com.xwc.esbatis.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/25  10:37
 * 业务：
 * 功能：
 */
public class QueryMate {
    private String name;
    private String colums;
    private List<FilterColumMate> queryFilter = new ArrayList<>();
    private FilterColumMate order;
    private FilterColumMate group;
    private FilterColumMate start;
    private FilterColumMate offset;
    private List<FilterColumMate> set = new ArrayList<>();

    public void addFilter(FilterColumMate columMate) {
        switch (columMate.getConditionEnum()) {
            case EQUEL:
            case NOT_EQUEL:
            case IN:
            case NOT_IN:
            case LIKE:
            case LEFT_LIKE:
            case RIGHT_LIKE:
            case NOT_LIKE:
            case NOT_LEFT_LIKE:
            case NOT_RIGHT_LIKE:
            case IS_NULL:
            case NOT_NULL:
                queryFilter.add(columMate);
                break;
            case GROUP_BY:
                group = columMate;
                break;
            case OEDER_ASC:
            case OEDER_DESC:
                order = columMate;
                break;
            case LIMIT_START:
                start = columMate;
                break;
            case LIMIT_OFFSET:
                offset = columMate;
                break;
            case SET:
                set.add(columMate);
        }

    }

    public List<FilterColumMate> getSet() {
        return set;
    }

    public QueryMate valid() {

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColums() {
        return colums;
    }

    public void setColums(String colums) {
        this.colums = colums;
    }

    public List<FilterColumMate> getQueryFilter() {
        return queryFilter;
    }

    public void setQueryFilter(List<FilterColumMate> queryFilter) {
        this.queryFilter = queryFilter;
    }

    public FilterColumMate getOrder() {
        return order;
    }

    public void setOrder(FilterColumMate order) {
        this.order = order;
    }

    public FilterColumMate getGroup() {
        return group;
    }

    public void setGroup(FilterColumMate group) {
        this.group = group;
    }

    public FilterColumMate getStart() {
        return start;
    }

    public void setStart(FilterColumMate start) {
        this.start = start;
    }

    public FilterColumMate getOffset() {
        return offset;
    }

    public void setOffset(FilterColumMate offset) {
        this.offset = offset;
    }

    public void clearPage() {
        this.start = null;
        this.offset = null;
    }
}
