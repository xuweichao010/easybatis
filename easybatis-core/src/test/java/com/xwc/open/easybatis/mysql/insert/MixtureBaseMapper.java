package com.xwc.open.easybatis.mysql.insert;

import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;

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

    @InsertSql
    Long insertBatchMixture(String tableName, List<E> listParam);

}
