package com.xwc.open.easybatis.supports;

import com.xwc.open.easybatis.Placeholder;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 15:34
 */
public class MybatisPlaceholder implements Placeholder {
    private static final String OPEN = "#{";

    private static final String CLOSE = "}";

    @Override
    public String holder(BatisColumnAttribute batisColumnAttribute) {
        if (batisColumnAttribute.isMulti()) {
            return OPEN + String.join(".", batisColumnAttribute.getPath()) + CLOSE;
        } else {
            return OPEN + batisColumnAttribute.getParameterName() + CLOSE;
        }

    }
}