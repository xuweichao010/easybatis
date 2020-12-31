package com.xwc.open.easybatis.mysql.condition;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Equal;
import com.xwc.open.easybatis.core.anno.condition.filter.GreaterThan;
import com.xwc.open.easybatis.core.anno.condition.filter.LessThan;
import com.xwc.open.easybatis.core.anno.condition.filter.NotEqual;
import com.xwc.open.easybatis.core.interfaces.BaseMapper;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/31
 * 描述：条件mapper
 */
public interface ConditionMapper extends BaseMapper<ConditionEntity, String> {

    @SelectSql
    void defaultEqual(String name);

    @SelectSql(dynamic = true)
    void defaultEqualDynamic(String name);

    @SelectSql
    void equalAnnotation(@Equal String name);

    @SelectSql
    void equalAnnotationDynamic(@Equal(dynamic = true) String name);

    @SelectSql
    void equalAnnotationCustomColumn(@Equal("custom_name") String name);

    @SelectSql
    void notEqualAnnotation(@NotEqual String name);

    @SelectSql
    void notEqualAnnotationDynamic(@NotEqual(dynamic = true) String name);

    @SelectSql
    void notEqualAnnotationCustom(@NotEqual("custom_name") String name);

    @SelectSql
    void greaterThanAnnotation(@GreaterThan Integer age);

    @SelectSql
    void greaterThanAnnotationDynamic(@GreaterThan(dynamic = true) Integer age);

    @SelectSql
    void greaterThanAnnotationCustom(@GreaterThan("custom_age") Integer age);

    @SelectSql
    void lessThanAnnotation(@LessThan Integer age);

    @SelectSql
    void lessThanAnnotationDynamic(@LessThan(dynamic = true) Integer age);

    @SelectSql
    void lessThanAnnotationCustom(@LessThan("age") Integer customAge);
}
