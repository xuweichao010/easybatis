package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.model.Placeholder;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/7/26
 * 描述：#
 */
public interface PlaceholderBuilder {
    Placeholder parameterHolder(String paramName, String parentName);

    Placeholder columnHolder(String alias,String column);
}
