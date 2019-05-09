package com.xwc.esbatis.anno.condition;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/25  10:35
 * 业务：
 * 功能：标识这是一个查询实体
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FilterBody {

}
