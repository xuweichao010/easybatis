package com.xwc.open.easybatis.supports;

import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.exceptions.ParamCheckException;
import com.xwc.open.easybatis.snippet.conditional.ConditionalSnippet;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:53
 */
public class DefaultConditionalRegistry implements ConditionalRegistry {
    private Map<Class<? extends Annotation>, Object> conditionalMap = new ConcurrentHashMap<>();

    @Override
    public void register(Class<? extends Annotation> annotationClass, ConditionalSnippet conditionalSnippet) {
        conditionalMap.put(annotationClass, conditionalSnippet);
    }

    @Override
    public Annotation choose(BatisColumnAttribute columnAttribute) {
        Annotation useAnnotation = null;
        for (Annotation annotation : columnAttribute.annotations()) {
            if (conditionalMap.containsKey(annotation.annotationType())) {
                if (useAnnotation != null) {
                    throw new ParamCheckException("一个参数上发现多个条件注解 " + columnAttribute.getParameterName());
                }
                useAnnotation = annotation;
            }
        }
        return null;
    }
}

