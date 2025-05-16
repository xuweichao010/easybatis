package cn.onetozero.easybatis.annotaions.page;

import cn.onetozero.easy.parse.annotations.Syntax;

import java.lang.annotation.*;

/**
 * @author  徐卫超
 * @since  2019/4/24  10:56
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Syntax(60002)
public @interface Limit {

}
