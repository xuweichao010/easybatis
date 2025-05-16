package cn.onetozero.easy.parse.model.parameter;

import cn.onetozero.easy.parse.model.PrimaryKeyAttribute;

/**
 * 类描述：用于描述方法上的基础参数的类型
 * @author  徐卫超 (cc)
 * @since 2022/11/25 23:32
 */
public class CollectionPrimaryKeyParameterAttribute extends PrimaryKeyParameterAttribute {
    public CollectionPrimaryKeyParameterAttribute(PrimaryKeyAttribute primaryKey) {
        super(primaryKey);
    }
}
