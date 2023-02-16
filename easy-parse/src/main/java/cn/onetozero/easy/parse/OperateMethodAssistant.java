package cn.onetozero.easy.parse;

import cn.onetozero.easy.parse.model.OperateMethodMeta;

import java.lang.reflect.Method;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 23:25
 */
public interface OperateMethodAssistant {

    OperateMethodMeta getOperateMethodMeta(Class<?> clazz, Method method);
}
