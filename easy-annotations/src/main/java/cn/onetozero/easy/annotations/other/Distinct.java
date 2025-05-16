package cn.onetozero.easy.annotations.other;

import cn.onetozero.easy.annotations.Syntax;

import java.lang.annotation.*;

/**
 * @author  徐卫超
 * @since  2019/4/24  10:56
 * 业务：根据方法参数构建一个删除的XML SQL 内容
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Syntax()
public @interface Distinct {

}
