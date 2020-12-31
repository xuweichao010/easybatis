package com.xwc.open.easybatis.mysql.condition;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/31
 * 描述：条件mapper
 */
public interface ConditionMapper extends BaseMapper<ConditionEntity, String> {

    @SelectSql
    void defaultEqual(String name);
}
