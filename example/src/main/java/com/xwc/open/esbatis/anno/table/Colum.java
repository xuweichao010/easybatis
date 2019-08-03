package com.xwc.open.esbatis.anno.table;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  14:33
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Colum {
    @AliasFor("value")
    String colum() default "";

    @AliasFor("colum")
    String value() default "";
}
