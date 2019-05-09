package com.xwc.esbatis.anno.table;

import com.xwc.esbatis.anno.enums.KeyEnum;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  15:13
 * 业务：
 * 功能：
 */

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
    KeyEnum type() default KeyEnum.AUTO;

    String colum() default "";

}
