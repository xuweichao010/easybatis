package com.xwc.open.esbatis.anno;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/29  14:30
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectSql {
    @AliasFor("colums")
    String value() default "";

    @AliasFor("value")
    String colums() default "";
}
