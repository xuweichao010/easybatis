package cn.onetozero.easybatis.snippet.from;

import cn.onetozero.easy.parse.model.OperateMethodMeta;

/**
 * 类描述：用于处理 Insert 语句中 INSERT INTO tableName 这部分语句的片段
 * @author  徐卫超 (cc)
 * @since 2023/1/16 13:45
 */
public interface SelectFromSnippet {

    String from(OperateMethodMeta tableMeta);
}
