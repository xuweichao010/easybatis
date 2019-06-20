package com.xwc.esbatis.anno.table;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  14:33
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD})     //只能使用在：类、接口、注解、枚举
@Retention(RetentionPolicy.RUNTIME)     //在运行时有效
public @interface IgnoreColum {
}
