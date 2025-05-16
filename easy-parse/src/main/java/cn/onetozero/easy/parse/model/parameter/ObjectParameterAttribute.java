package cn.onetozero.easy.parse.model.parameter;

import cn.onetozero.easy.parse.model.ParameterAttribute;

/**
 * 类描述：描述一个自定义对象集合
 * @author  徐卫超 (cc)
 * @since 2022/11/25 23:31
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
