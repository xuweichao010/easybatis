package com.xwc.open.easybatis.core.interfaces.condition;

import com.xwc.open.easybatis.core.support.ParamMeta;

import java.util.List;

/**
 * 作者：徐卫超 cc
 * 时间：2021/1/5
 * 描述：
 */
public interface GroupByCondition {

    public String apply(List<ParamMeta> list);
}
