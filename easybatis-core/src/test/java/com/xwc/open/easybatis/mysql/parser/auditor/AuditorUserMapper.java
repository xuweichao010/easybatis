package com.xwc.open.easybatis.mysql.parser.auditor;

import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.auditor.*;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.anno.table.Id;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.support.BaseMapper;
import lombok.Data;

import java.util.Date;


public interface AuditorUserMapper extends BaseMapper<AuditorUser, String> {

    @UpdateSql
    int updateParam(@SetParam String name, String id);

}
