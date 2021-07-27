package com.xwc.open.easybatis.core.anno;


import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/29  14:30
 * 业务：根据方法参数构建一个查询的XML SQL 内容
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SelectSql {

    String value() default "";

    /**
     * query对象查询查询时dynamic 自动为true
     *
     * @return
     */
    boolean dynamic() default false;


    String from() default "";

    /**
     * @return A database id that correspond this statement
     * @since 3.5.5
     */
    String databaseId() default "";


}
