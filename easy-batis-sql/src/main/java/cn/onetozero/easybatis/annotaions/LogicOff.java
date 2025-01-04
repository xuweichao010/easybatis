package cn.onetozero.easybatis.annotaions;

import cn.onetozero.easy.parse.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2025/1/3  10:56
 * 业务：当查询方法支持逻辑字段时，该注解会直接关闭SQL语句对逻辑字段的支持。
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Syntax(10001)
public @interface LogicOff {
}
