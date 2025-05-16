package cn.onetozero.easy.annotations.other;

import cn.onetozero.easy.annotations.Syntax;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  10:56
 * 业务：统计数量 当方法上出现了@Count注解后  Order和Page 片段奖不在执行
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Syntax()
public @interface Count {

}
