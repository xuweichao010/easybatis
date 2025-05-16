package cn.onetozero.easy.annotations.order;

import cn.onetozero.easy.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 类描述：固定排序注解，该注解只能使用在方法上 这个注解和参数排序注解@Order、@Asc、@Desc注解之间会存在冲突
 * 如果在一个方法上出现@OrderBy和其他排序注解 只处理@OrderBy注解 参数排序注解不会参与到SQL语句的构建中
 * @author  徐卫超 (cc)
 * @since 2023/1/31 11:10
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
