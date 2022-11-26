package com.xwc.open.easy.core.assistant;

import com.xwc.open.easy.core.model.DatabaseMeta;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:19
 */
public interface DataBaseModelAssistant {

    DatabaseMeta getDatabaseMeta(Class<?> clazz);

}
