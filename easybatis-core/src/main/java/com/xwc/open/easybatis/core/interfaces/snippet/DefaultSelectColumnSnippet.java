package com.xwc.open.easybatis.core.interfaces.snippet;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.support.MethodMeta;

import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public class DefaultSelectColumnSnippet implements SelectColumnSnippet {

    public String apply(MethodMeta methodMeta) {
        SelectSql selectSql = methodMeta.chooseAnnotationType(SelectSql.class);
        if (StringUtils.hasText(selectSql.value())) {
            return selectSql.value();
        }
        return methodMeta.selectColumnList().stream()
                .map(column -> "`" + column.getColumn() + "`").collect(Collectors.joining(", "));
    }
}
