package com.xwc.open.easybatis.mybatis.condition;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Equal;
import com.xwc.open.easybatis.core.anno.condition.filter.GreaterThan;
import com.xwc.open.easybatis.core.anno.condition.filter.NotEqual;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.interfaces.EasyMapper;
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
public interface ConditionTableUserMapper extends EasyMapper<ConditionTableUser, String> {

    @SelectSql
    List<ConditionTableUser> defaultEqual(@Ignore String tableName, String name);

    @SelectSql(dynamic = true)
    List<ConditionTableUser> defaultEqualDynamic(@Ignore String tableName, String name);

    @SelectSql
    List<ConditionTableUser> equalAnnotation(@Ignore String tableName, @Equal String name);

    @SelectSql
    List<ConditionTableUser> equalAnnotationDynamic(@Ignore String tableName, @Equal(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> equalAnnotationCustomColumn(@Ignore String tableName, @Equal("name") String customName);

    @SelectSql
    List<ConditionTableUser> notEqualAnnotation(@Ignore String tableName, @NotEqual String name);

    @SelectSql
    List<ConditionTableUser> notEqualAnnotationDynamic(@Ignore String tableName, @NotEqual(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> notEqualAnnotationCustom(@Ignore String tableName, @NotEqual("custom_name") String name);

    @SelectSql
    List<ConditionTableUser> greaterThanAnnotation(@Ignore String tableName, @GreaterThan Integer age);

    @SelectSql
    List<ConditionTableUser> greaterThanAnnotationDynamic(@Ignore String tableName, @GreaterThan(dynamic = true) Integer age);

    @SelectSql
    List<ConditionTableUser> greaterThanAnnotationCustom(@Ignore String tableName, @GreaterThan("custom_age") Integer age);

}
