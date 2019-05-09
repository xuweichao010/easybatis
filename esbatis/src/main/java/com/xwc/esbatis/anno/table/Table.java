package com.xwc.esbatis.anno.table;

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
    String value();
}
