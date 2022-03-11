package com.xwc.open.easybatis.mysql.parser.update;

import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.In;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.support.BaseMapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：更新测试mapper
 */
public interface UpdateMapper<E, K> extends BaseMapper<E, K> {

    @UpdateSql
    void updateKey(@In List<K> ids, @SetParam Integer status);


}
