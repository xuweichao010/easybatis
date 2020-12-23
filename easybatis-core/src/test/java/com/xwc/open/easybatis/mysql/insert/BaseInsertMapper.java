package com.xwc.open.easybatis.mysql.insert;

import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;
import org.apache.ibatis.annotations.Insert;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：插入测试mapper
 */
public interface BaseInsertMapper extends EasyMapper<BaseInsertEntity, String> {

    @InsertSql
    void insert(BaseInsertEntity e);
}
