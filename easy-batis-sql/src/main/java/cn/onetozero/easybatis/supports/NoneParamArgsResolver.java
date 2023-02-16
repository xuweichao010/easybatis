package cn.onetozero.easybatis.supports;

import cn.onetozero.easy.parse.model.OperateMethodMeta;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.Map;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/6 12:27
 */
public class NoneParamArgsResolver implements ParamArgsResolver {

    @Override
    public void methodParams(Map<String, Object> methodParams, OperateMethodMeta operateMethodMeta, SqlCommandType sqlCommandType) {

    }
}
