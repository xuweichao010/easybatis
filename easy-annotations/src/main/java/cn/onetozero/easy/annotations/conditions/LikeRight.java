package cn.onetozero.easy.annotations.conditions;

import cn.onetozero.easy.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务： 条件注解
 * 功能： 右模糊匹配 column LIKE 'value%'
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Syntax()
public @interface LikeRight {

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
     * 在JOIN条件中使用
     *
     * @return 返回属性的别名
     */
    String alias() default "";


}
