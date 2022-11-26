package com.xwc.open.easy.core.model.parameter;

import com.xwc.open.easy.core.model.ParameterAttribute;
import com.xwc.open.easy.core.model.PrimaryKeyAttribute;

import java.util.List;

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

    @Override
    public List<ParameterAttribute> insert() {
        return null;
    }

    @Override
    public List<ParameterAttribute> update() {
        return null;
    }

    @Override
    public List<ParameterAttribute> select() {
        return null;
    }

    @Override
    public List<ParameterAttribute> delete() {
        return null;
    }
}
