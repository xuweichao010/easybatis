package com.xwc.open.easybatis.snippet.from;

import com.xwc.open.easy.parse.model.TableMeta;

/**
 * 类描述：用户不配置的时候 框架默认的用于处理 Insert 语句中 INSERT INTO tableName 这部分语句的片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 13:46
 */
public class DefaultInsertFrom implements InsertFromSnippet {

    @Override
    public String from(TableMeta tableMeta) {
        return " INSERT INTO " + tableMeta.getTableName();
    }
}
