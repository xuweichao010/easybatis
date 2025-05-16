package cn.onetozero.easy.parse.model.parameter;

import cn.onetozero.easy.parse.model.ParameterAttribute;
import cn.onetozero.easy.parse.model.TableMeta;

/**
 * 类描述：描述的这个对象是实体
 * @author  徐卫超 (cc)
 * @since 2022/11/25 23:31
 */
public class EntityParameterAttribute extends ParameterAttribute {

    public TableMeta databaseMeta;

    public EntityParameterAttribute(TableMeta databaseMeta) {
        this.databaseMeta = databaseMeta;
    }

    public TableMeta getDatabaseMeta() {
        return databaseMeta;
    }
}
