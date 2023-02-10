package com.xwc.open.easybatis.snippet.from;

import com.xwc.open.easy.parse.model.OperateMethodMeta;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/2 11:32
 */
public class DefaultDeleteFromSnippet implements DeleteFromSnippet {
    @Override
    public String from(OperateMethodMeta tableMeta) {
        return " DELETE FROM " + tableMeta.getDatabaseMeta().getTableName();
    }
}
