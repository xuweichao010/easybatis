package com.xwc.open.easybatis.mysql.other;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.Count;
import com.xwc.open.easybatis.core.anno.condition.Distinct;
import com.xwc.open.easybatis.core.anno.condition.filter.ASC;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/31
 * 描述：条件mapper
 */
public interface OtherMapper extends BaseMapper<OtherEntity, String> {

    @SelectSql
    @Count
    int countAll();

    @SelectSql
    @Count
    int countCondition(String name, String age);

    @SelectSql
    @Distinct
    List<OtherEntity> distinctAll();

    @SelectSql
    @Distinct
    List<OtherEntity> distinctCondition(String name, String age);


    @SelectSql("id")
    @Distinct
    List<OtherEntity> distinctCustomCondition(String name, String age);

    @Distinct
    @Count
    @SelectSql("id")
    List<OtherEntity> distinctCountCondition(String name, String age);


    @SelectSql("id")
    List<OtherEntity> orderBy(String name, @ASC("name") Boolean names);

}
