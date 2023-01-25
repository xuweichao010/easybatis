package com.xwc.open.easybatis.snippet.from;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.TableMeta;

/**
 * 类描述：用于处理 Insert 语句中 INSERT INTO tableName 这部分语句的片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 13:45
 */
public interface SelectFromSnippet {

    String from(OperateMethodMeta tableMeta);
}
