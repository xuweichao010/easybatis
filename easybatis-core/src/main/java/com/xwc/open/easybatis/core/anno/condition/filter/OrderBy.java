package com.xwc.open.easybatis.core.anno.condition.filter;

import com.xwc.open.easybatis.core.enums.ConditionType;

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
@Condition(type = ConditionType.ORDER_BY)
public @interface OrderBy {
    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    String value() default "";


    /**
     * 是否是动态语句
     *
     * @return
     */
    boolean dynamic() default false;

}
