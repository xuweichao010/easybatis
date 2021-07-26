package com.xwc.open.easybatis.core.anno.table;

import com.xwc.open.easybatis.core.anno.condition.filter.Condition;
import com.xwc.open.easybatis.core.enums.ConditionType;

import java.lang.annotation.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/1/18  14:33
 * 业务：
 * 功能：
 */
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})     //只能使用在：类、接口、注解、枚举
@Retention(RetentionPolicy.RUNTIME)     //在运行时有效
@Condition(type = ConditionType.IGNORE)
public @interface Ignore {
}
