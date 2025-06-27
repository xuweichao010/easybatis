package cn.onetozero.easy.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
@Syntax(10001)
public @interface OffLogic {

}
