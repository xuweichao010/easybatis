package com.xwc.open.easybatis.mysql.mybatis.condition;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
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
    List<ConditionTableUser> notEqualAnnotationCustom(@Ignore String tableName, @NotEqual("name") String customName);

    @SelectSql
    List<ConditionTableUser> greaterThanAnnotation(@Ignore String tableName, @GreaterThan Integer age);

    @SelectSql
    List<ConditionTableUser> greaterThanAnnotationDynamic(@Ignore String tableName, @GreaterThan(dynamic = true) Integer age);

    @SelectSql
    List<ConditionTableUser> greaterThanAnnotationCustom(@Ignore String tableName, @GreaterThan("age") Integer customAge);

    @SelectSql
    List<ConditionTableUser> lessThanAnnotation(@Ignore String tableName, @LessThan Integer age);

    @SelectSql
    List<ConditionTableUser> lessThanAnnotationDynamic(@Ignore String tableName, @LessThan(dynamic = true) Integer age);

    @SelectSql
    List<ConditionTableUser> lessThanAnnotationCustom(@Ignore String tableName, @LessThan("age") Integer customAge);

    @SelectSql
    List<ConditionTableUser> greaterThanEqualAnnotation(@Ignore String tableName, @GreaterThanEqual Integer age);

    @SelectSql
    List<ConditionTableUser> greaterThanEqualAnnotationDynamic(@Ignore String tableName, @GreaterThanEqual(dynamic = true) Integer age);

    @SelectSql
    List<ConditionTableUser> greaterThanEqualAnnotationCustom(@Ignore String tableName, @GreaterThanEqual("age") Integer customAge);

    @SelectSql
    List<ConditionTableUser> lessThanEqualAnnotation(@Ignore String tableName, @LessThanEqual Integer age);

    @SelectSql
    List<ConditionTableUser> lessThanEqualAnnotationDynamic(@Ignore String tableName, @LessThanEqual(dynamic = true) Integer age);

    @SelectSql
    List<ConditionTableUser> lessThanEqualAnnotationCustom(@Ignore String tableName, @LessThanEqual("age") Integer customAge);

    @SelectSql
    List<ConditionTableUser> isNullAnnotation(@Ignore String tableName, @IsNull Boolean name);

    @SelectSql
    List<ConditionTableUser> isNullAnnotationDynamic(@Ignore String tableName, @IsNull(dynamic = true) Boolean name);

    @SelectSql
    List<ConditionTableUser> isNullAnnotationCustom(@Ignore String tableName, @IsNull(value = "name", dynamic = true) Boolean customName);

    @SelectSql
    List<ConditionTableUser> isNotNullAnnotation(@Ignore String tableName, @IsNotNull Boolean name);

    @SelectSql
    List<ConditionTableUser> isNotNullAnnotationDynamic(@Ignore String tableName, @IsNotNull(dynamic = true) Boolean name);

    @SelectSql
    List<ConditionTableUser> isNotNullAnnotationCustom(@Ignore String tableName, @IsNotNull(value = "name", dynamic = true) Boolean customName);

    @SelectSql
    List<ConditionTableUser> like(@Ignore String tableName, @Like String name);

    @SelectSql
    List<ConditionTableUser> likeDynamic(@Ignore String tableName, @Like(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> likeCustom(@Ignore String tableName, @Like(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionTableUser> leftLike(@Ignore String tableName, @LeftLike String name);

    @SelectSql
    List<ConditionTableUser> leftLikeDynamic(@Ignore String tableName, @LeftLike(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> leftLikeCustom(@Ignore String tableName, @LeftLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionTableUser> rightLike(@Ignore String tableName, @RightLike String name);

    @SelectSql
    List<ConditionTableUser> rightLikeDynamic(@Ignore String tableName, @RightLike(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> rightLikeCustom(@Ignore String tableName, @RightLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionTableUser> notLike(@Ignore String tableName, @NotLike String name);

    @SelectSql
    List<ConditionTableUser> notLikeDynamic(@Ignore String tableName, @NotLike(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> notLikeCustom(@Ignore String tableName, @NotLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionTableUser> notLeftLike(@Ignore String tableName, @NotLeftLike String name);

    @SelectSql
    List<ConditionTableUser> notLeftLikeDynamic(@Ignore String tableName, @NotLeftLike(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> notLeftLikeCustom(@Ignore String tableName, @NotLeftLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionTableUser> notRightLike(@Ignore String tableName, @NotRightLike String name);

    @SelectSql
    List<ConditionTableUser> notRightLikeDynamic(@Ignore String tableName, @NotRightLike(dynamic = true) String name);

    @SelectSql
    List<ConditionTableUser> notRightLikeCustom(@Ignore String tableName, @NotRightLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionTableUser> in(@Ignore String tableName, @In(value = "name") List<String> name);

    @SelectSql
    List<ConditionTableUser> inDynamic(@Ignore String tableName, @In(value = "name", dynamic = true) List<String> name);

    @SelectSql
    List<ConditionTableUser> inCustom(@Ignore String tableName, @In(value = "name", dynamic = true) List<String> customName);

    @SelectSql
    List<ConditionTableUser> notIn(@Ignore String tableName, @NotIn(value = "name") List<String> name);

    @SelectSql
    List<ConditionTableUser> notInDynamic(@Ignore String tableName, @NotIn(value = "name", dynamic = true) List<String> name);

    @SelectSql
    List<ConditionTableUser> notInCustom(@Ignore String tableName, @NotIn(value = "name", dynamic = true) List<String> customName);
}
