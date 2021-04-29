package com.xwc.open.easybatis.mysql.mybatis.other;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.Count;
import com.xwc.open.easybatis.core.anno.condition.Distinct;
import com.xwc.open.easybatis.core.anno.condition.filter.ASC;
import com.xwc.open.easybatis.core.anno.condition.filter.DESC;
import com.xwc.open.easybatis.core.support.EasyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface OtherUserMapper extends EasyMapper<OtherUser, String> {

    @SelectSql
    List<OtherUser> orderByDesc(@DESC Boolean age);

    @SelectSql
    List<OtherUser> orderByAsc(@ASC Boolean age);

    @SelectSql
    List<OtherUser> orderByMultiDesc(@DESC Boolean age, @ASC Boolean job);

    @SelectSql(dynamic = true)
    List<OtherUser> orderByMethodDynamic(@ASC Boolean age);

    @SelectSql(dynamic = true)
    List<OtherUser> orderByMethodDynamicMulti(@ASC Boolean age, @DESC Boolean job);

    @SelectSql
    List<OtherUser> orderByParamDynamic(@ASC(dynamic = true) Boolean age);

    @SelectSql(dynamic = true)
    List<OtherUser> orderByParamDynamicMulti(@ASC(dynamic = true) Boolean age, @DESC(dynamic = true) Boolean job);

    @SelectSql
    @Count
    Integer count();

    @SelectSql(" age ")
    @Distinct
    List<Integer> distinctAge();


}
