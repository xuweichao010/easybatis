package com.xwc.open.easybatis;

import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easybatis.MapperEasyAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.builder.annotation.MethodResolver;

import java.lang.reflect.Method;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 9:56
 */
public class EasyMethodResolver extends MethodResolver {
    private final MapperEasyAnnotationBuilder annotationBuilder;
    private final Method method;

    public EasyMethodResolver(MapperEasyAnnotationBuilder annotationBuilder, Method method) {
        super(null, null);
        this.annotationBuilder = annotationBuilder;
        this.method = method;
    }


    public void resolve() {
        annotationBuilder.parseStatement(method);
    }
}
