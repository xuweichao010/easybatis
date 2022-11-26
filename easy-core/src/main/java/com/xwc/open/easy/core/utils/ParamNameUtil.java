package com.xwc.open.easy.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ParamNameUtil {
    /**
     * 获取参数的名称
     *
     * @param method 需要获取的方法上的参数名称的列表
     * @return
     */
    public static List<String> getParamNames(Method method) {
        return getParameterNames(method);
    }

    public static List<String> getParamNames(Constructor<?> constructor) {
        return getParameterNames(constructor);
    }

    private static List<String> getParameterNames(Executable executable) {
        return (List<String>) Arrays.stream(executable.getParameters()).map(Parameter::getName).collect(Collectors.toList());
    }

    private ParamNameUtil() {
    }
}
