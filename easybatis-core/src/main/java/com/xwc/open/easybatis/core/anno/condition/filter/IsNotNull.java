package com.xwc.open.easybatis.core.anno.condition.filter;

import com.xwc.open.easybatis.core.enums.SyntaxPosition;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务：空查询
 * 功能：
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Condition(SyntaxPosition.CONDITION)
public @interface IsNotNull {




    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    String value() default "";

    /**
     * query对象查询查询时dynamic 自动为true
     *
     * @return 标识是否使用动态查询
     */
    boolean dynamic() default false;


    /**
     * 条件别名
     * 在JOIN条件中使用
     *
     * @return 返回属性的别名
     */
    String alias() default "";
}
