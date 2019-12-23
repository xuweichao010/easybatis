package com.xwc.open.easybatis.anno;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  10:56
 * 业务：根据方法参数构建一个删除的XML SQL 内容
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DeleteSql {
    @AliasFor("colums")
    String value() default "";

    @AliasFor("value")
    String colums() default "";
}
