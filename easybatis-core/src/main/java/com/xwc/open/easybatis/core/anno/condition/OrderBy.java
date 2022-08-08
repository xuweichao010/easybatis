package com.xwc.open.easybatis.core.anno.condition;

import com.xwc.open.easybatis.core.anno.condition.filter.Condition;
import com.xwc.open.easybatis.core.enums.SyntaxPosition;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Condition(SyntaxPosition.ORDER)
public @interface OrderBy {
    /**
     * 属性和数据表之间的列关系
     *
     * @return
     */
    String value() default "";
}
