package com.xwc.open.esbatis.anno.condition.filter;

import org.springframework.core.annotation.AliasFor;

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
public @interface Equal {

    @AliasFor("value")
    String colum() default "";

    @AliasFor("colum")
    String value() default "";

}
