package com.xwc.open.easybatis.mysql.mybatis.auditor;


import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.support.BaseTableMapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuditorTableUserMapper extends BaseTableMapper<AuditorTableUser, String> {

    @UpdateSql
    Integer updateParam(@Ignore String tableName, @SetParam String name, String id);
}
