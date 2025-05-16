package cn.onetozero.easy.annotations.set;

import cn.onetozero.easy.annotations.Syntax;

import java.lang.annotation.*;

/**
 * @author  徐卫超
 * @since  2019/4/24  13:01
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

    /**
     * query对象查询查询时dynamic 自动为true
     *
     * @return 标识是否使用动态查询
     */
    boolean dynamic() default false;

    /**
     * 条件别名
     * 在JOIN情况下中使用
     *
     * @return 返回属性的别名
     */
    String alias() default "";


}
