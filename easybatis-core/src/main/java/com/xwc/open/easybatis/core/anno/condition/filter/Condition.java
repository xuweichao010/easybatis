package com.xwc.open.easybatis.core.anno.condition.filter;

import com.xwc.open.easybatis.core.enums.SyntaxPosition;

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
    /**
     * 语法在SQL那个阶段参与构建,可以控制注解的处理器在那个阶段触发
     *
     * @return
     */
    SyntaxPosition value();
}
