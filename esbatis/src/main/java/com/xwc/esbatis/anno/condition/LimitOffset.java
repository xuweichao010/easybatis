package com.xwc.esbatis.anno.condition;

import com.xwc.esbatis.anno.enums.ConditionEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Condition(conditionType = ConditionEnum.LIMIT_OFFSET)
public @interface LimitOffset {
    @AliasFor(annotation = Condition.class, attribute = "index")
    int index() default 99;

    @AliasFor(annotation = Condition.class, attribute = "colum")
    String colum() default "";
}
