package com.xwc.open.easybatis.annotaions;


import com.xwc.open.easy.parse.annotations.Syntax;

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
@Syntax(10001)
public @interface SelectSql {

    /**
     * 需要查询的列
     *
     * @return
     */
    String value() default "";

    /**
     * @return A database id that correspond this statement
     * @since 3.5.5
     */
    String databaseId() default "";

    /**
     * 查询join模式当为空的时候默认不开启，join值有内容的时候就会会处理成连表查询
     *
     * @return
     */
    String join() default "";

}
