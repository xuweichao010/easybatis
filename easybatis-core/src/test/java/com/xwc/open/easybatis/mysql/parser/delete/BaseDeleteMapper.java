package com.xwc.open.easybatis.mysql.parser.delete;

import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Equal;
import com.xwc.open.easybatis.core.support.BaseMapper;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/23
 * 描述：插入测试mapper
 */
public interface BaseDeleteMapper extends BaseMapper<BaseDeleteMapper, String> {

    @DeleteSql
    Object deleteParam(String orgName);

    @DeleteSql
    Object deleteParamDynamic(@Equal(dynamic = true) String orgName);

}
