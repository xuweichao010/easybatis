package com.xwc.open.easybatis.annotaions.set;

import com.xwc.open.easy.parse.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Syntax(60001)
public @interface SetParam {


    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    String value() default "";


}
