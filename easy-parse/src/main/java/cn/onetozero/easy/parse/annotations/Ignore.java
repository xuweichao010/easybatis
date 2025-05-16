package cn.onetozero.easy.parse.annotations;

import java.lang.annotation.*;

/**
 * @author  徐卫超
 * @since  2019/1/18  14:33
 * 业务：忽略属性或者参数
 * 功能：
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})     //只能使用在：类、接口、注解、枚举
@Retention(RetentionPolicy.RUNTIME)     //在运行时有效
@Syntax
public @interface Ignore {
}
