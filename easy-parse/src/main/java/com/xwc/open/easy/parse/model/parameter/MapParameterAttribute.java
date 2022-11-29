package com.xwc.open.easy.parse.model.parameter;

import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.PrimaryKeyAttribute;

/**
 * 类描述：参数是map的属性
 * 在更新和删除的时候需要使用到主键类型
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:32
 */
public class MapParameterAttribute extends ParameterAttribute {
    private final PrimaryKeyAttribute primaryKey;

    public MapParameterAttribute(PrimaryKeyAttribute primaryKey) {
        this.primaryKey = primaryKey;
    }

}
