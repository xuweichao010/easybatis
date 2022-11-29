package com.xwc.easy.core.method;

import com.xwc.easy.core.table.method.MethodEntity;
import com.xwc.open.easy.parse.supports.EasyMapper;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/27 20:57
 */
public interface BaseMapper<E, K> extends EasyMapper<E, K> {

    int commonInsert(MethodEntity e);

    int commonUpdate(MethodEntity e);

    int commonDel(K id);

    int commonGet(K id);
}
