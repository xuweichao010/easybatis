package com.xwc.open.easybatis.mysql.mybatis.other;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.ASC;
import com.xwc.open.easybatis.core.anno.condition.filter.DESC;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.support.EasyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/2  11:19
 * 业务：
 * 功能：
 */
@SuppressWarnings("unused")
@Mapper
public interface OtherTableUserMapper extends EasyMapper<OtherTableUser, String> {

    @SelectSql
    List<OtherTableUser> orderByDesc(@Ignore String tableName, @DESC Boolean age);

    @SelectSql
    List<OtherTableUser> orderByAsc(@Ignore String tableName, @ASC Boolean age);

    @SelectSql
    List<OtherTableUser> orderByMultiDesc(@Ignore String tableName, @DESC Boolean age, @ASC Boolean job);

    @SelectSql(dynamic = true)
    List<OtherTableUser> orderByMethodDynamic(@Ignore String tableName, @ASC Boolean age);

    @SelectSql(dynamic = true)
    List<OtherTableUser> orderByMethodDynamicMulti(@Ignore String tableName, @ASC Boolean age, @DESC Boolean job);

    @SelectSql
    List<OtherTableUser> orderByParamDynamic(@Ignore String tableName, @ASC(dynamic = true) Boolean age);

    @SelectSql(dynamic = true)
    List<OtherTableUser> orderByParamDynamicMulti(@Ignore String tableName, @ASC(dynamic = true) Boolean age, @DESC(dynamic = true) Boolean job);

}
