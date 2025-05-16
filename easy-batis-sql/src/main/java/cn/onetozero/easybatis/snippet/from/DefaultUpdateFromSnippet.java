package cn.onetozero.easybatis.snippet.from;

import cn.onetozero.easy.parse.model.OperateMethodMeta;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/1 14:39
 */
public class DefaultUpdateFromSnippet implements UpdateFromSnippet {

    @Override
    public String from(OperateMethodMeta tableMeta) {
        return " UPDATE " + tableMeta.getDatabaseMeta().getTableName();
    }
}
