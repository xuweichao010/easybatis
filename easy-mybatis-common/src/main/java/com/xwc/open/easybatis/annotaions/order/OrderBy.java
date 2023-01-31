package com.xwc.open.easybatis.annotaions.order;

import com.xwc.open.easy.parse.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 类描述：排序注解 使用在方法上 这个注解和 参数注解order asc  desc 注解之间会存在冲突
 * 作者：徐卫超 (cc)
 * 时间 2023/1/31 11:10
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Syntax(10001)
public @interface OrderBy {

    /**
     * 排序的语法  ORDER BY 之后的语法内容 需要遵循SQL语法来进行填写
     * 框架不会对这部分语句进行任何处理 一般应用于固定的排序规则
     */
    String value() default "";




}
