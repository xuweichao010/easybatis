package com.xwc.open.easybatis.mybatis.condition;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/17
 * 描述：用户数据读取
 */
@Mapper
public interface ConditionUserMapper extends EasyMapper<ConditionUser, String> {
    @SelectSql
    List<ConditionUser> defaultEqual(String name);

    @SelectSql(dynamic = true)
    List<ConditionUser> defaultEqualDynamic(String name);

    @SelectSql
    List<ConditionUser> equalAnnotation(@Equal String name);

    @SelectSql
    List<ConditionUser> equalAnnotationDynamic(@Equal(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> equalAnnotationCustomColumn(@Equal("name") String CustomName);

    @SelectSql
    List<ConditionUser> notEqualAnnotation(@NotEqual String name);

    @SelectSql
    List<ConditionUser> notEqualAnnotationDynamic(@NotEqual(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> notEqualAnnotationCustom(@NotEqual("name") String customName);

    @SelectSql
    List<ConditionUser> greaterThanAnnotation(@GreaterThan Integer age);

    @SelectSql
    List<ConditionUser> greaterThanAnnotationDynamic(@GreaterThan(dynamic = true) Integer age);

    @SelectSql
    List<ConditionUser> greaterThanAnnotationCustom(@GreaterThan("age") Integer customAge);

    @SelectSql
    List<ConditionUser> lessThanAnnotation(@LessThan Integer age);

    @SelectSql
    List<ConditionUser> lessThanAnnotationDynamic(@LessThan(dynamic = true) Integer age);

    @SelectSql
    List<ConditionUser> lessThanAnnotationCustom(@LessThan("age") Integer customAge);

    @SelectSql
    List<ConditionUser> greaterThanEqualAnnotation(@GreaterThanEqual Integer age);

    @SelectSql
    List<ConditionUser> greaterThanEqualAnnotationDynamic(@GreaterThanEqual(dynamic = true) Integer age);

    @SelectSql
    List<ConditionUser> greaterThanEqualAnnotationCustom(@GreaterThanEqual("age") Integer customAge);

    @SelectSql
    List<ConditionUser> lessThanEqualAnnotation(@LessThanEqual Integer age);

    @SelectSql
    List<ConditionUser> lessThanEqualAnnotationDynamic(@LessThanEqual(dynamic = true) Integer age);

    @SelectSql
    List<ConditionUser> lessThanEqualAnnotationCustom(@LessThanEqual("age") Integer customAge);

}
