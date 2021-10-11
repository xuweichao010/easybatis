package com.xwc.open.easybatis.core.anno.condition;

import com.xwc.open.easybatis.core.anno.condition.filter.Condition;
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
@Condition(type = ConditionType.ORDER_BY_ASC)
public @interface ASC {
    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    String value() default "";


    boolean dynamic() default false;

    /**
     * 条件别名
     * 在JOIN条件中使用
     *
     * @return
     */
    String alias() default "";
}


