package com.xwc.open.easybatis.mysql.parser.auditor;

import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.support.BaseMapper;


public interface AuditorUserMapper extends BaseMapper<AuditorUser, String> {

    @UpdateSql
    int updateParam(@SetParam String name, String id);

}
