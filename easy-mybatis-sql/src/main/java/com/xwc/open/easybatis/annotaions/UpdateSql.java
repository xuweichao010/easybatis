package com.xwc.open.easybatis.annotaions;

import com.xwc.open.easy.parse.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  10:56
 * 业务：根据方法参数构建一个更新的XML SQL内容
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Syntax(10001)
public @interface UpdateSql {


    /**
     * @return A database id that correspond this statement
     * @since 3.5.5
     */
    String databaseId() default "";
}
