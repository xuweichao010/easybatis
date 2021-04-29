package com.xwc.open.easybatis.mysql.parser.insert;

import com.xwc.open.easybatis.core.anno.InsertSql;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：插入测试mapper
 */
public interface BaseInsertMapper extends MixtureBaseMapper<BaseInsertEntity, String> {

    @InsertSql
    Object insertEntity(BaseInsertEntity entity);

    @InsertSql
    Object insertMulti(String tableName, BaseInsertEntity entity);

    @InsertSql
    Object insertBatchEntity(List<BaseInsertEntity> list);

    @InsertSql
    Object insertBatchEntityMixture(String tableName, List<BaseInsertEntity> listParam);

}
