package com.xwc.esbatis.anno.condition;

import com.xwc.esbatis.anno.enums.ConditionEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:02
 * 业务：
 * 功能：
 */

@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    ConditionEnum conditionType() default ConditionEnum.EQUEL;
}
