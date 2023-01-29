package com.xwc.open.easybatis.supports;

import com.xwc.open.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 15:34
 */
public class MybatisPlaceholder implements BatisPlaceholder {
    private static final String OPEN = "#{";

    private static final String CLOSE = "}";

    @Override
    public String holder(BatisColumnAttribute batisColumnAttribute) {
        return OPEN + path(batisColumnAttribute) + CLOSE;

    }

    @Override
    public String path(BatisColumnAttribute columnAttribute) {
        if (columnAttribute.isMulti()) {
            return String.join(".", columnAttribute.getPath());
        } else {
            return columnAttribute.getParameterName();
        }
    }
}
