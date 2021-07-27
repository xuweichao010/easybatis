package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.support.snippet.FromSnippet;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/7/26
 * 描述：
 */
public class MySqlFromSnippet implements FromSnippet {

    @Override
    public String apply(MethodMeta methodMeta) {
        String from = methodMeta.optionalStringAttributes("from");
        if (StringUtils.hasText(from)) {
            return from;
        } else {
            return methodMeta.getTableMetadata().getTableName();
        }
    }
}
