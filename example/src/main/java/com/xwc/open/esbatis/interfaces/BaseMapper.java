package com.xwc.open.esbatis.interfaces;

import com.xwc.open.esbatis.anno.DeleteSql;
import com.xwc.open.esbatis.anno.SelectParam;
import com.xwc.open.esbatis.anno.InsertSql;
import com.xwc.open.esbatis.anno.UpdateSql;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:19
 * 业务：
 * 功能：
 */
@SuppressWarnings("unused")
public interface BaseMapper<E, K> {

    @SelectParam
    E selectKey(K id);

    @InsertSql
    void insert(E entity);

    @InsertSql
    void insertBatch(List<E> list);

    @UpdateSql
    void update(E entity);

    @DeleteSql
    long delete(K id);

}
