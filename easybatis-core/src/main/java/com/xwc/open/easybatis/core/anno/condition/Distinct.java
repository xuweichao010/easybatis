package com.xwc.open.easybatis.core.anno.condition;

import com.xwc.open.easybatis.core.anno.condition.filter.Condition;
import com.xwc.open.easybatis.core.enums.SyntaxPosition;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/4/24  10:56
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Condition(SyntaxPosition.SELECT)
public @interface Distinct {
}
