package com.xwc.open.easy.parse.model.parameter;

import com.xwc.open.easy.parse.model.ParameterAttribute;

/**
 * 类描述：描述一个自定义对象集合
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:31
 */
public class ObjectParameterAttribute extends ParameterAttribute {
    private final Class<?> objectClass;

    public ObjectParameterAttribute(Class<?> objectClass) {
        this.objectClass = objectClass;
    }

    public Class<?> getObjectClass() {
        return objectClass;
    }
}
