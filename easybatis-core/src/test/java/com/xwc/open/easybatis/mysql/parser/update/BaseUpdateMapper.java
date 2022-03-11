package com.xwc.open.easybatis.mysql.parser.update;

import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.support.BaseMapper;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：更新测试mapper
 */
public interface BaseUpdateMapper extends UpdateMapper<BaseUpdateEntity, String> {
    @UpdateSql
    Object updateEntity(BaseUpdateEntity entity);

    @UpdateSql
    Object updateEntityMixture(@Ignore String tableName, BaseUpdateEntity entity);

    @UpdateSql
    Object updateParamCondition(@SetParam String orgCode, @SetParam String orgName, String id);

    @UpdateSql
    Object updateParam(@SetParam String orgCode, @SetParam String orgName);

    @UpdateSql(dynamic = true)
    Object updateEntityDynamic(BaseUpdateEntity entity);

    @UpdateSql(dynamic = true)
    Object updateEntityDynamicMixture(@Ignore String tableName, BaseUpdateEntity entity);


}
