package com.xwc.open.easybatis.anno.table;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  11:46
 * 业务：
 * 功能：
 */

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * 指定实体对应的表名
     * @return
     */
    @AliasFor("name")
    String value() default "";


    /**
     * 指定实体对应的表名
     * @return
     */
    @AliasFor("value")
    String name() default "";
}
