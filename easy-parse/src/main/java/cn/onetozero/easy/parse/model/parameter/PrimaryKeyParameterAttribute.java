package cn.onetozero.easy.parse.model.parameter;

import cn.onetozero.easy.parse.model.ParameterAttribute;
import cn.onetozero.easy.parse.model.PrimaryKeyAttribute;

/**
 * 类描述：用于描述方法上的基础参数的类型
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:32
 */
public class PrimaryKeyParameterAttribute extends ParameterAttribute {
    private final PrimaryKeyAttribute primaryKey;

    public PrimaryKeyParameterAttribute(PrimaryKeyAttribute primaryKey) {
        this.primaryKey = primaryKey;
    }

    public PrimaryKeyAttribute getPrimaryKey() {
        return primaryKey;
    }
}
