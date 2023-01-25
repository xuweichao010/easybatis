package com.xwc.open.easy.parse.model.parameter;

import com.xwc.open.easy.parse.model.TableMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;

import java.util.List;

/**
 * 类描述：描述的这个对象是实体
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:31
 */
public class EntityParameterAttribute extends ParameterAttribute {

    public TableMeta databaseMeta;

    public EntityParameterAttribute(TableMeta databaseMeta) {
        this.databaseMeta = databaseMeta;
    }
}
