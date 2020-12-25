package com.xwc.open.easybatis.mysql.insert;

import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：插入测试mapper
 */
public interface BaseInsertMapper extends BaseMapper<BaseInsertEntity, String> {

    @InsertSql
    Object insertEntity(BaseInsertEntity entity);

    @InsertSql
    Object insertMulti(String tableName, BaseInsertEntity entity);

    @InsertSql
    Object insertBatch(List<BaseInsertEntity> list);

}
