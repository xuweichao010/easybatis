package com.xwc.open.easybatis;

import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 15:31
 */
public interface Placeholder {

    String holder(BatisColumnAttribute batisColumnAttribute);
}
