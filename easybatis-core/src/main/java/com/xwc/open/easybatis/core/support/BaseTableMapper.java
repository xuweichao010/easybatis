package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.table.Ignore;

import java.util.Collection;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:19
 * 业务：
 * 功能：
 */
@SuppressWarnings("unused")
public interface BaseTableMapper<E, K> extends EasyMapper<E, K> {

    @SelectSql
    E selectKey(@Ignore String tableName, K id);

    @UpdateSql
    Integer update(@Ignore String tableName, E entity);

    @UpdateSql(dynamic = true)
    Integer updateActivate(@Ignore String tableName, E entity);

    @InsertSql
    Integer insert(@Ignore String tableName, E entity);

    @InsertSql
    Integer insertBatch(@Ignore String tableName, Collection<E> list);

    @DeleteSql
    Integer delete(@Ignore String tableName, K id);

}
