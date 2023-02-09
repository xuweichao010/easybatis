package com.xwc.open.easybatis.snippet.from;

import com.xwc.open.easy.parse.model.OperateMethodMeta;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 14:39
 */
public class DefaultUpdateFromSnippet implements UpdateFromSnippet {

    @Override
    public String from(OperateMethodMeta tableMeta) {
        return " UPDATE " + tableMeta.getDatabaseMeta().getTableName();
    }
}
