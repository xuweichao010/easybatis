package cn.onetozero.easy.parse;

import cn.onetozero.easy.parse.model.TableMeta;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/24 15:19
 */
public interface TableMetaAssistant {

    TableMeta getTableMeta(Class<?> clazz);

}
