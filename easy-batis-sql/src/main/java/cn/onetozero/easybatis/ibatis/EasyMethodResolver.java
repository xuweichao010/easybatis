package cn.onetozero.easybatis.ibatis;

import org.apache.ibatis.builder.annotation.MethodResolver;

import java.lang.reflect.Method;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/17 9:56
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
