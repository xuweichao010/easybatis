package cn.onetozero.easybatis.annotaions.order;

import cn.onetozero.easy.parse.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/31 11:12
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Syntax(10001)
public @interface Desc {
    /**
     * 排序属性的列 如果不填写使用属性或者参数名来作为列属性
     *
     * @return
     */
    String value() default "";

    /**
     * 是否支持动态排序
     */
    boolean dynamic() default false;

}
