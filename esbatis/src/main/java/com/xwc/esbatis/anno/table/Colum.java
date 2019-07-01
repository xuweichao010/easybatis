package com.xwc.esbatis.anno.table;

import com.xwc.esbatis.meta.FieldType;

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
@Operation(FieldType.GENREAL)
public @interface Colum {
    String colum() default "";
}
