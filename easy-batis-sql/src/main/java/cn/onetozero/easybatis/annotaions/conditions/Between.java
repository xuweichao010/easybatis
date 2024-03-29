package cn.onetozero.easybatis.annotaions.conditions;

import cn.onetozero.easy.parse.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务： 条件注解
 * 功能： 范围标识 column BETWEEN value1 AND value2
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Syntax()
public @interface Between {

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

    /**
     * 关联属性
     * <p>
     * 必须和@Between注解被标识的属性同时在参数上或者同一对象中
     *
     * @return
     */
    String of() default "";

}
