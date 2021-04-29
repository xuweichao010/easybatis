package com.xwc.open.easybatis.mysql.parser.insert;

import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.support.BaseMapper;

import java.util.Collection;
import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:19
 * 业务：
 * 功能：
 */
@SuppressWarnings("unused")
public interface MixtureBaseMapper<E, K> extends BaseMapper<E, K> {

    @SelectSql
    E selectKey(K id);

    @UpdateSql
    Integer update(E entity);

    @InsertSql
    Integer insert(E entity);

    @InsertSql
    Integer insertBatch(Collection<E> list);

    @DeleteSql
    Integer delete(K id);

    @InsertSql
    Long insertBatchMixture(String tableName, List<E> listParam);

}
