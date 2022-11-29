package com.xwc.open.easy.parse;

import com.xwc.open.easy.parse.model.TableMeta;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:19
 */
public interface TableMetaAssistant {

    TableMeta getTableMeta(Class<?> clazz);

}
