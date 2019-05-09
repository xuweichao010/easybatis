package com.xwc.esbatis.anno;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/25  20:28
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@GeneratSql
public @interface GeneratSql {
}
