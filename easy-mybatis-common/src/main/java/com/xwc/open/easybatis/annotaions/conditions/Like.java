package com.xwc.open.easybatis.annotaions.conditions;

import com.xwc.open.easy.parse.annotations.Syntax;

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
@Syntax(30001)
public @interface Like {

    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    String value() default "";

    /**
     * 条件别名
     * 在JOIN条件中使用
     *
     * @return
     */
    String alias() default "";

    /**
     * query对象查询查询时dynamic 自动为true
     *
     * @return
     */
    boolean dynamic() default false;
}
