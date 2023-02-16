package com.xwc.easy.core.method;

import cn.onetozero.easy.parse.supports.EasyMapper;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/27 20:57
 */
public interface BaseMapper<E, K> extends EasyMapper<E, K> {

    int genericsEntityParameterAttribute(E e);

    int genericsCollectionEntityParameterAttribute(List<E> e);

    List<E> genericsCollectionPrimaryKeyParameterAttribute(List<K> ids);

    int genericsPrimaryKeyParameterAttribute(K id);
}
