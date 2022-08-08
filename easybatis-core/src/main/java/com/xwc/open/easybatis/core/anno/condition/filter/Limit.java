package com.xwc.open.easybatis.core.anno.condition.filter;

import com.xwc.open.easybatis.core.enums.SyntaxPosition;

import java.lang.annotation.*;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/7/25 17:38
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Condition(SyntaxPosition.PAGE)
public @interface Limit {

    String relation() default "";
}
