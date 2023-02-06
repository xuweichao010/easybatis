package com.xwc.open.easybatis.supports;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.Map;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/6 12:24
 */
public interface ParamArgsResolver {

    void methodParams(Map<String, Object> methodParams, OperateMethodMeta operateMethodMeta,
                      SqlCommandType sqlCommandType);


}
