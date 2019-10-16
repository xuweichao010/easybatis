package com.xwc.open.easy.batis.anno.condition.filter;

import com.xwc.open.easy.batis.enums.ConditionType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Condition(type = ConditionType.LEFT_LIKE)
public @interface LeftLike {

    /**
     * 查询条件排序
     *
     * @return
     */
    int index() default 99;

    /**
     * 关联的数据库的列  默认使用属性名
     *
     * @return
     */
    @AliasFor("value")
    String colum() default "";


    /**
     * 关联的数据库的列  默认使用属性名
     *
     * @return
     */
    @AliasFor("colum")
    String value() default "";

}
