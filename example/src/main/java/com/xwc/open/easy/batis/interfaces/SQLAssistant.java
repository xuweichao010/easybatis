package com.xwc.open.easy.batis.interfaces;

import com.xwc.open.easy.batis.anno.ParamKey;
import com.xwc.open.easy.batis.anno.condition.Count;
import com.xwc.open.easy.batis.anno.condition.Distinct;
import com.xwc.open.easy.batis.meta.Condition;
import com.xwc.open.easy.batis.meta.EntityMate;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/8  18:42
 * 业务：
 * 功能：定义相关sql片段的实现逻辑
 */
public interface SQLAssistant {

    StringBuilder delete(EntityMate entity, Condition condition, boolean isObject, boolean isDynamic, ParamKey key);

    StringBuilder select(EntityMate entity, Condition condition, boolean isObject, boolean isDynamic, Count count, Distinct distinct, ParamKey key);

    StringBuilder insert(EntityMate entity, boolean isBatch);

    StringBuilder update(EntityMate entity, Condition condition, boolean isUpdateParam, boolean isDynamic);


}
