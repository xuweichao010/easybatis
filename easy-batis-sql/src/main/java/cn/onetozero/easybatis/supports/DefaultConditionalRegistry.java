package cn.onetozero.easybatis.supports;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.exceptions.ParamCheckException;
import cn.onetozero.easybatis.snippet.conditional.ConditionalSnippet;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:53
 */
public class DefaultConditionalRegistry implements ConditionalRegistry {
    private Map<Class<? extends Annotation>, ConditionalSnippet> conditionalMap = new ConcurrentHashMap<>();

    @Override
    public void register(Class<? extends Annotation> annotationClass, ConditionalSnippet conditionalSnippet) {
        conditionalMap.put(annotationClass, conditionalSnippet);
    }

    @Override
    public Annotation chooseAnnotation(BatisColumnAttribute columnAttribute) {
        Annotation useAnnotation = null;
        for (Annotation annotation : columnAttribute.annotations()) {
            if (conditionalMap.containsKey(annotation.annotationType())) {
                if (useAnnotation != null) {
                    throw new ParamCheckException("一个参数上发现多个条件注解 " + columnAttribute.getParameterName());
                }
                useAnnotation = annotation;
            }
        }
        return useAnnotation;
    }

    @Override
    public ConditionalSnippet chooseSnippet(Class<? extends Annotation> annotation) {
        return conditionalMap.get(annotation);
    }


}

