package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.Count;
import com.xwc.open.easybatis.core.anno.condition.Distinct;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.support.snippet.SelectColumnSnippet;

import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public class DefaultSelectColumnSnippet implements SelectColumnSnippet {

    public String apply(MethodMeta methodMeta) {
        SelectSql selectSql = methodMeta.chooseAnnotationType(SelectSql.class);
        String selectColumn;
        boolean isDefault = true;
        if (StringUtils.hasText(selectSql.value())) {
            selectColumn = selectSql.value();
            isDefault = false;
        } else {
            selectColumn = methodMeta.selectColumnList().stream()
                    .map(column -> "`" + column.getColumn() + "`").collect(Collectors.joining(", "));
        }
        String distinct = distinct(methodMeta.chooseAnnotationType(Distinct.class), selectColumn);
        if (distinct.equals(selectColumn)) {
            return count(methodMeta.chooseAnnotationType(Count.class), distinct, isDefault);
        } else {
            return count(methodMeta.chooseAnnotationType(Count.class), distinct, false);
        }

    }

    public String count(Count count, String selectColumns, boolean isDefault) {
        if (count == null) {
            return selectColumns;
        } else {
            if (isDefault) {
                return "COUNT(*)";
            } else {
                return "COUNT(" + selectColumns + ")";
            }
        }
    }

    public String distinct(Distinct distinct, String selectColumns) {
        if (distinct == null) {
            return selectColumns;
        } else {
            return "DISTINCT(" + selectColumns + ")";
        }
    }
}
