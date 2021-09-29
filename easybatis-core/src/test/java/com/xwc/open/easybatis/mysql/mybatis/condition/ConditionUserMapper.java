package com.xwc.open.easybatis.mysql.mybatis.condition;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.support.EasyMapper;
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
    List<ConditionUser> findAll();

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

    @SelectSql
    List<ConditionUser> isNullAnnotation(@IsNull Boolean name);

    @SelectSql
    List<ConditionUser> isNullAnnotationDynamic(@IsNull(dynamic = true) Boolean name);

    @SelectSql
    List<ConditionUser> isNullAnnotationCustom(@IsNull(value = "name", dynamic = true) Boolean customName);

    @SelectSql
    List<ConditionUser> isNotNullAnnotation(@IsNotNull Boolean name);

    @SelectSql
    List<ConditionUser> isNotNullAnnotationDynamic(@IsNotNull(dynamic = true) Boolean name);

    @SelectSql
    List<ConditionUser> isNotNullAnnotationCustom(@IsNotNull(value = "name", dynamic = true) Boolean customName);

    @SelectSql
    List<ConditionUser> like(@Like String name);

    @SelectSql
    List<ConditionUser> likeDynamic(@Like(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> likeCustom(@Like(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionUser> leftLike(@LeftLike String name);

    @SelectSql
    List<ConditionUser> leftLikeDynamic(@LeftLike(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> leftLikeCustom(@LeftLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionUser> rightLike(@RightLike String name);

    @SelectSql
    List<ConditionUser> rightLikeDynamic(@RightLike(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> rightLikeCustom(@RightLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionUser> notLike(@NotLike String name);

    @SelectSql
    List<ConditionUser> notLikeDynamic(@NotLike(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> notLikeCustom(@NotLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionUser> notLeftLike(@NotLeftLike String name);

    @SelectSql
    List<ConditionUser> notLeftLikeDynamic(@NotLeftLike(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> notLeftLikeCustom(@NotLeftLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionUser> notRightLike(@NotRightLike String name);

    @SelectSql
    List<ConditionUser> notRightLikeDynamic(@NotRightLike(dynamic = true) String name);

    @SelectSql
    List<ConditionUser> notRightLikeCustom(@NotRightLike(value = "name", dynamic = true) String customName);

    @SelectSql
    List<ConditionUser> in(@In(value = "name") List<String> name);

    @SelectSql
    List<ConditionUser> inDynamic(@In(value = "name", dynamic = true) List<String> name);

    @SelectSql
    List<ConditionUser> inCustom(@In(value = "name", dynamic = true) List<String> customName);

    @SelectSql
    List<ConditionUser> notIn(@NotIn(value = "name") List<String> name);

    @SelectSql
    List<ConditionUser> notInDynamic(@NotIn(value = "name", dynamic = true) List<String> name);

    @SelectSql
    List<ConditionUser> notInCustom(@NotIn(value = "name", dynamic = true) List<String> customName);


}
