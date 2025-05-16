package cn.onetozero.easybatis.supports;

import cn.onetozero.easy.parse.model.OperateMethodMeta;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.Map;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/6 12:24
 */
public interface ParamArgsResolver {

    void methodParams(Map<String, Object> methodParams, OperateMethodMeta operateMethodMeta,
                      SqlCommandType sqlCommandType);


}
