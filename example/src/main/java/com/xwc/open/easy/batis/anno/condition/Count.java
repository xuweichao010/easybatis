package com.xwc.open.easy.batis.anno.condition;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  10:56
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Count {

    @AliasFor("value")
    String colums() default "*";

    @AliasFor("colums")
    String value() default "*";
}
