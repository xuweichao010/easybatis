package com.xwc.open.easy.core.supports;

import com.xwc.open.easy.core.model.DatabaseModel;
import com.xwc.open.easy.core.model.FillAttribute;

import java.lang.reflect.Field;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:19
 */
public interface DataBaseModelAssistant {

    DatabaseModel getResultModel(Class<?> clazz);




}
