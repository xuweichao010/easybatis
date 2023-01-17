package com.xwc.open.easybatis.mapper;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.annotaions.InsertSql;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:22
 */
public interface BaseMapper<E, K> extends EasyMapper<E, K> {

    @InsertSql
    int insert(E normalUser);

    @InsertSql
    int insertIgnore(@Ignore String tableName, E user);

    @InsertSql
    int insertBatch(List<E> users);

    @InsertSql
    int insertBatchIgnore(@Ignore String tableName, List<E> users);
}
