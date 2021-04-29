package com.xwc.open.easybatis.core.anno.table;

import com.xwc.open.easybatis.core.enums.IdType;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  15:13
 * 业务：
 * 功能：
 */

@Documented
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Id {
    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    String value() default "";


    /**
     * 主键类型
     *
     * @return
     */
    IdType type() default IdType.GLOBAL;


}
