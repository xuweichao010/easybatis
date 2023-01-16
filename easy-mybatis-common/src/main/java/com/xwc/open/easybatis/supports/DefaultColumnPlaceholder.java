package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easybatis.Placeholder;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 15:34
 */
public class DefaultColumnPlaceholder implements Placeholder {
    private static final String HOLDER = "`";

    @Override
    public String holder(BatisColumnAttribute batisColumnAttribute) {
        return HOLDER + batisColumnAttribute.getColumn() + HOLDER;
    }
}
