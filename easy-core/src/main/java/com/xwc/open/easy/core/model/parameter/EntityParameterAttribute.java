package com.xwc.open.easy.core.model.parameter;

import com.xwc.open.easy.core.model.DatabaseMeta;
import com.xwc.open.easy.core.model.ParameterAttribute;

import java.util.List;

/**
 * 类描述：描述的这个对象是实体
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:31
 */
public class EntityParameterAttribute extends ObjectParameterAttribute {

    public DatabaseMeta databaseMeta;

    public EntityParameterAttribute(DatabaseMeta databaseMeta) {
        super(databaseMeta.getSource());
        this.databaseMeta = databaseMeta;
    }

    @Override
    public List<ParameterAttribute> insert() {
        return super.insert();
    }

    @Override
    public List<ParameterAttribute> update() {
        return super.update();
    }

    @Override
    public List<ParameterAttribute> select() {
        return super.select();
    }

    @Override
    public List<ParameterAttribute> delete() {
        return super.delete();
    }
}
