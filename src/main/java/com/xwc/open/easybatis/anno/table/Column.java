package com.xwc.open.easybatis.anno.table;

import org.springframework.core.annotation.AliasFor;

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
public @interface Column {
    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    @AliasFor("value")
    String column() default "";


    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    @AliasFor("column")
    String value() default "";

    /**
     * 查询是否查询该字段 false 查询 true 不查询
     *
     * @return
     */
    boolean selectIgnore() default false;

    /**
     * 是否更新该字段 false更新 true不更新
     *
     * @return
     */
    boolean updateIgnore() default false;


    /**
     * 是否插入该字段 false插入 true不插入
     *
     * @return
     */
    boolean insertIgnore() default false;
}
