package com.xwc.open.easybatis.mysql.parser.condition;

import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.support.BaseMapper;

import java.util.List;

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

    @SelectSql
    void greaterThanEqualAnnotation(@GreaterThanEqual Integer age);

    @SelectSql
    void greaterThanEqualAnnotationDynamic(@GreaterThanEqual(dynamic = true) Integer age);

    @SelectSql
    void greaterThanEqualAnnotationCustom(@GreaterThanEqual("age") Integer customAge);

    @SelectSql
    void lessThanEqualAnnotation(@LessThanEqual Integer age);

    @SelectSql
    void lessThanEqualAnnotationDynamic(@LessThanEqual(dynamic = true) Integer age);

    @SelectSql
    void lessThanEqualAnnotationCustom(@LessThanEqual("age") Integer customAge);

    @SelectSql
    void isNullAnnotation(@IsNull Boolean name);

    @SelectSql
    void isNullAnnotationDynamic(@IsNull(dynamic = true) Boolean name);

    @SelectSql
    void isNullAnnotationCustom(@IsNull(value = "name", dynamic = true) Boolean customName);

    @SelectSql
    void isNotNullAnnotation(@IsNotNull Boolean name);

    @SelectSql
    void isNotNullAnnotationDynamic(@IsNotNull(dynamic = true) Boolean name);

    @SelectSql
    void isNotNullAnnotationCustom(@IsNotNull(value = "name", dynamic = true) Boolean customName);

    @SelectSql
    void like(@Like String name);

    @SelectSql
    void likeDynamic(@Like(dynamic = true) String name);

    @SelectSql
    void likeCustom(@Like(value = "name", dynamic = true) String customName);

    @SelectSql
    void leftLike(@LeftLike String name);

    @SelectSql
    void leftLikeDynamic(@LeftLike(dynamic = true) String name);

    @SelectSql
    void leftLikeCustom(@LeftLike(value = "name", dynamic = true) String customName);

    @SelectSql
    void rightLike(@RightLike String name);

    @SelectSql
    void rightLikeDynamic(@RightLike(dynamic = true) String name);

    @SelectSql
    void rightLikeCustom(@RightLike(value = "name", dynamic = true) String customName);

    @SelectSql
    void notLike(@NotLike String name);

    @SelectSql
    void notLikeDynamic(@NotLike(dynamic = true) String name);

    @SelectSql
    void notLikeCustom(@NotLike(value = "name", dynamic = true) String customName);

    @SelectSql
    void notLeftLike(@NotLeftLike String name);

    @SelectSql
    void notLeftLikeDynamic(@NotLeftLike(dynamic = true) String name);

    @SelectSql
    void notLeftLikeCustom(@NotLeftLike(value = "name", dynamic = true) String customName);

    @SelectSql
    void notRightLike(@NotRightLike String name);

    @SelectSql
    void notRightLikeDynamic(@NotRightLike(dynamic = true) String name);

    @SelectSql
    void notRightLikeCustom(@NotRightLike(value = "name", dynamic = true) String customName);

    @SelectSql
    void in(@In(value = "name") List<String> name);

    @SelectSql
    void inDynamic(@In(value = "name", dynamic = true) List<String> name);

    @SelectSql
    void inCustom(@In(value = "name", dynamic = true) List<String> customName);

    @SelectSql
    void notIn(@NotIn(value = "name") List<String> name);

    @SelectSql
    void notInDynamic(@NotIn(value = "name", dynamic = true) List<String> name);

    @SelectSql
    void notInCustom(@NotIn(value = "name", dynamic = true) List<String> customName);
}
