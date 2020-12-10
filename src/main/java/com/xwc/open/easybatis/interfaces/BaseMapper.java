package com.xwc.open.easybatis.interfaces;

import com.xwc.open.easybatis.anno.DeleteSql;
import com.xwc.open.easybatis.anno.InsertSql;
import com.xwc.open.easybatis.anno.SelectSql;
import com.xwc.open.easybatis.anno.UpdateSql;
import com.xwc.open.easybatis.anno.condition.PrimaryKey;

import java.util.Collection;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:19
 * 业务：
 * 功能：
 */
@SuppressWarnings("unused")
public interface BaseMapper<E, K> extends EasyMapper<E, K> {

    @SelectSql
    @PrimaryKey
    E selectKey(K id);

    @UpdateSql
    Long update(E entity);

    @InsertSql
    Long insert(E entity);

    @InsertSql
    Long insertBatch(Collection<E> list);

    @DeleteSql
    Long delete(K id);

}
