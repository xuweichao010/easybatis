package com.xwc.open.easy.parse;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;

import java.util.List;

/**
 * 类描述：根据操作来分析所需要的参数
 * 作者：徐卫超 (cc)
 * 时间 2022/11/29 18:51
 */
public interface ParameterAttributeMetaAssistant {

    List<ParameterAttribute> insert(OperateMethodMeta methodMeta);

    List<ParameterAttribute> delete(OperateMethodMeta methodMeta);

    List<ParameterAttribute> update(OperateMethodMeta methodMeta);

    List<ParameterAttribute> query(OperateMethodMeta methodMeta);


}
