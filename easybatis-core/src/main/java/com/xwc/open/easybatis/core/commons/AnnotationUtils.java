package com.xwc.open.easybatis.core.commons;

import com.xwc.open.easybatis.core.anno.condition.Count;
import com.xwc.open.easybatis.core.anno.condition.filter.Condition;
import com.xwc.open.easybatis.core.anno.table.Column;
import com.xwc.open.easybatis.core.anno.table.Table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/14
 * 描述：注解工具类
 */
public class AnnotationUtils {
    public static <T extends Annotation> T findAnnotation(Field field, Class<T> annotationClass) {
        return field.getAnnotation(annotationClass);
    }

    public static <T extends Annotation> T findAnnotation(Method method, Class<T> annotationClass) {
        return method.getAnnotation(annotationClass);
    }


    public static <T extends Annotation> T findAnnotation(Class<?> typeClass, Class<T> annotationClass) {
        return typeClass.getAnnotation(annotationClass);
    }

    public static Object getValue(Table tableAnno, String name) {
        return new Object();
    }

    public static Map<String, Object> getAnnotationAttributes(Annotation annotation) {
        Class<? extends Annotation> aClass = annotation.getClass();
        Field[] fields = aClass.getFields();
        return Arrays.stream(fields).collect(Collectors.toMap(field -> {
            return field.getName();
        }, field -> {
            //return  field.get(annotation);
            return new Object();
        }));
    }


}
