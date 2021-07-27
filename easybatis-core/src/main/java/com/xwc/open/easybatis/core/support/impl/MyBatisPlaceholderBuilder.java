package com.xwc.open.easybatis.core.support.impl;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.model.Placeholder;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/7/26
 * 描述：
 */
public class MyBatisPlaceholderBuilder implements PlaceholderBuilder {

    @Override
    public Placeholder parameterHolder(String paramName, String parentName) {
        Placeholder placeholder = new Placeholder();
        placeholder.setParamPath(StringUtils.isEmpty(parentName) ? paramName : (parentName + "." + paramName));
        placeholder.setHolder("#{" + placeholder.getParamPath() + "}");
        return placeholder;
    }

    @Override
    public Placeholder columnHolder(String alias, String column) {
        Placeholder placeholder = new Placeholder();
        String columnHolder = "`" + column + "`";
        if (StringUtils.hasText(alias)) {
            columnHolder = alias + "." + columnHolder;
        }
        placeholder.setHolder(columnHolder);
        placeholder.setParamPath(column);
        return placeholder;
    }
}


