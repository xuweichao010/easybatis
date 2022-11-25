package com.xwc.open.easy.core.annotations;


import com.xwc.open.easy.core.enums.IdType;

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
     */
    String value() default "";

    /**
     * 主键类型
     * GLOBAL: 使用配置的主键类型
     * AUTO: 自增长数据类型 交给数据库来决定
     * UUID: UUID来作为数据主键
     * INPUT: 以用户提供的主键来作为标准
     *
     */
    IdType type() default IdType.GLOBAL;


}
