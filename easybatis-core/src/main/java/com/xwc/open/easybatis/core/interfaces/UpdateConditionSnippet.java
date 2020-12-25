package com.xwc.open.easybatis.core.interfaces;

import com.xwc.open.easybatis.core.support.ParamMeta;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public interface UpdateConditionSnippet {
    String apply(ParamMeta metaData);
}
