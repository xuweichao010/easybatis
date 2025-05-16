package cn.onetozero.easybatis.snippet.from;

import cn.onetozero.easy.parse.model.OperateMethodMeta;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/2 11:32
 */
public class DefaultDeleteFromSnippet implements DeleteFromSnippet {
    @Override
    public String from(OperateMethodMeta tableMeta) {
        return " DELETE FROM " + tableMeta.getDatabaseMeta().getTableName();
    }
}
