package com.xwc.esbatis.interfaces;

import com.xwc.esbatis.anno.GenerateDelete;
import com.xwc.esbatis.anno.GenerateInsert;
import com.xwc.esbatis.anno.GenerateSelectOne;
import com.xwc.esbatis.anno.GenerateUpdate;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:19
 * 业务：
 * 功能：
 */
@SuppressWarnings("unused")
public interface BaseMapper<E, K> {

    @GenerateSelectOne
    E selectKey(K id);

    @GenerateInsert
    void insert(E entity);

    @GenerateInsert
    void insertBatch(List<E> list);

    @GenerateUpdate
    void update(E entity);

    @GenerateDelete
    long delete(K id);

}
