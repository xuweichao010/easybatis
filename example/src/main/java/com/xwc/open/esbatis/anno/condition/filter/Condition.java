package com.xwc.open.esbatis.anno.condition.filter;

import com.xwc.open.esbatis.enums.ConditionType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  13:01
 * 业务：条件标记
 * 功能：
 */
@Documented
@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    ConditionType type();
}
