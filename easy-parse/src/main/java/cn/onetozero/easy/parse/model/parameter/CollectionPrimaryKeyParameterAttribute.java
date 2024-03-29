package cn.onetozero.easy.parse.model.parameter;

import cn.onetozero.easy.parse.model.PrimaryKeyAttribute;

/**
 * 类描述：用于描述方法上的基础参数的类型
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:32
 */
public class CollectionPrimaryKeyParameterAttribute extends PrimaryKeyParameterAttribute {
    public CollectionPrimaryKeyParameterAttribute(PrimaryKeyAttribute primaryKey) {
        super(primaryKey);
    }
}
