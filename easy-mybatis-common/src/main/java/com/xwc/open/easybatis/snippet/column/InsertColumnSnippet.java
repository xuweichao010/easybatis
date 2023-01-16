package com.xwc.open.easybatis.snippet.column;

import com.xwc.open.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：当用户不定义的时候 框架默认处理 Insert 语句中 INSERT INTO tableName 语句后面的 (col1,col2,col3) 这部分语句的片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 14:07
 */
public interface InsertColumnSnippet {

    String columns(List<BatisColumnAttribute> columnAttribute);
}
