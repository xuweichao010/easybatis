package com.xwc.open.easybatis.interfaces;

import com.xwc.open.easybatis.anno.ParamKey;
import com.xwc.open.easybatis.anno.condition.Count;
import com.xwc.open.easybatis.anno.condition.Distinct;
import com.xwc.open.easybatis.meta.ConditionMate;
import com.xwc.open.easybatis.meta.EntityMate;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/8  18:42
 * 业务：
 * 功能：定义相关sql片段的实现逻辑
 */
public interface SQLAssistant {

    StringBuilder delete(EntityMate entity, ConditionMate condition, boolean isObject, boolean isDynamic, ParamKey key);

    StringBuilder select(EntityMate entity, ConditionMate condition, boolean isObject, boolean isDynamic, Count count, Distinct distinct, ParamKey key);

    StringBuilder insert(EntityMate entity, boolean isBatch);

    StringBuilder update(EntityMate entity, ConditionMate condition, boolean isUpdateParam, boolean isDynamic);


}