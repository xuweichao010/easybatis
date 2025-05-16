package cn.onetozero.easy.annotations;


import cn.onetozero.easy.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/29  14:30
 * 业务：根据方法参数构建一个查询的XML SQL 内容
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Syntax()
public @interface SelectJoinSql {

    /**
     * 需要查询的列
     *
     * @return
     */
    String value() default "";


    /**
     * 关联的表
     *
     * @return
     */
    String from() default "";


    /**
     * @return A database id that correspond this statement
     * @since 3.5.5
     */
    String databaseId() default "";


}
