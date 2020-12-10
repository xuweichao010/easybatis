package com.xwc.open.easybatis.anno.condition.filter;

import com.xwc.open.easybatis.enums.ConditionType;
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
@Condition(type = ConditionType.RIGHT_LIKE)
public @interface RightLike {

    /**
     * 查询条件排序
     *
     * @return
     */
    int index() default 99;

    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    @AliasFor("value")
    String column() default "";


    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    @AliasFor("column")
    String value() default "";

    /**
     * 条件别名
     * 在JOIN条件中使用
     *
     * @return
     */
    String alias() default "";

}
