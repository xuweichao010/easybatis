package com.xwc.open.easybatis.supports;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Method;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/4 15:52
 */
public class EasyMethodSignature extends MapperMethod.MethodSignature {

    public EasyMethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
        super(configuration, mapperInterface, method);
    }
}
