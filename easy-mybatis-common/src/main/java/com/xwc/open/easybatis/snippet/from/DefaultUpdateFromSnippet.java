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
        // 逻辑删除注解使用的事DeleteSql 但是实际走的是更新逻辑 UpdateSql不一定存在
//        UpdateSql updateSql = tableMeta.findAnnotation(UpdateSql.class);
//        if (updateSql != null) {
//            return " UPDATE " + updateSql.join();
//        } else {
//            return " UPDATE " + tableMeta.getDatabaseMeta().getTableName();
//        }
        return " UPDATE " + tableMeta.getDatabaseMeta().getTableName();
    }
}
