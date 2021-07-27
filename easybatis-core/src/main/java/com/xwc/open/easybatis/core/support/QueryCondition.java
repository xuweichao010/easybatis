package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.model.ParamMapping;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 15:07
 * 备注：
 */
public interface QueryCondition {

    String apply(ParamMapping metaData);

}
