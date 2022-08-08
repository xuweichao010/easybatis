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

    /**
     * 需要查询的列
     *
     * @return
     */
    String value() default "";

    /**
     * query对象查询查询时dynamic 自动为true
     *
     * @return
     */
    boolean dynamic() default false;


    /**
     * 查询join模式当为空的时候默认不开启，join值有内容的时候就会会处理成连表查询
     *
     * @return
     */
    String join() default "";

}
