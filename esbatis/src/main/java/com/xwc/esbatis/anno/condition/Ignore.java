package com.xwc.esbatis.anno.condition;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/25  14:14
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Condition()
public @interface Ignore {
}
