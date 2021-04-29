package com.xwc.open.easybatis.core.commons;

import lombok.Data;

import java.awt.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
        T annotation = field.getAnnotation(annotationClass);
        if (annotation != null) return annotation;
        return findAnnotationArray(field.getAnnotations(), annotationClass);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T findAnnotationArray(Annotation[] annotations, Class<T> annotationClass) {
        if (annotations.length == 0) {
            return null;
        }
        for (Annotation annotation : annotations) {
            if (annotation.getClass().getClassLoader() == null) {
                continue;
            }
            if (annotation.annotationType().equals(annotationClass)) {
                return (T) annotation;
            }
            T innerAnnotation = findAnnotation(annotation.annotationType(), annotationClass);
            if (innerAnnotation != null) {
                return innerAnnotation;
            }
        }
        return null;

    }


    public static <T extends Annotation> T findAnnotation(Method method, Class<T> annotationClass) {
        T annotation = method.getDeclaredAnnotation(annotationClass);
        if (annotation == null) {
            annotation = findAnnotationArray(method.getDeclaredAnnotations(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return annotation;
    }


    public static <T extends Annotation> T findAnnotation(Class<?> typeClass, Class<T> annotationClass) {
        T annotation = typeClass.getDeclaredAnnotation(annotationClass);
        if (annotation == null) {
            annotation = findAnnotationArray(typeClass.getDeclaredAnnotations(), annotationClass);
            if (annotation != null) {
                return annotation;
            }
        }
        return annotation;
    }

    public static Object getValue(Annotation annotation, String name) {
        if (annotation != null && name != null) {
            try {
                Method declaredMethod = annotation.annotationType().getDeclaredMethod(name);
                return declaredMethod.invoke(annotation);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        }
        return null;
    }

    public static Map<String, Object> getAnnotationAttributes(Annotation annotation) {
        return Arrays.stream(annotation.annotationType().getDeclaredMethods()).collect(Collectors.toMap(Method::getName, method -> {
            try {
                return method.invoke(annotation);
            } catch (IllegalAccessException | InvocationTargetException e) {
                return null;
            }
        }));
    }

    @SuppressWarnings("unchecked")
    public static AnnotationMate findAnnotationMate(Field field, Class<? extends Annotation> annotationClass) {
        Annotation[] fieldDeclaredAnnotations = field.getDeclaredAnnotations();
        if (fieldDeclaredAnnotations.length == 0) {
            return null;
        }
        for (Annotation annotation : fieldDeclaredAnnotations) {
            AnnotationMate annotationMate = findAnnotationMate(annotation, annotationClass);
            if(annotationMate != null){
                return annotationMate;
            }
        }
        return null;
    }

    public static AnnotationMate findAnnotationMate(Annotation annotation, Class<? extends Annotation> annotationClass) {
        if (annotation.getClass().getClassLoader() == null){
            return null;
        }
        if (annotation.annotationType().equals(annotationClass)) {
            return new AnnotationMate(null, annotation);
        }
        Annotation[] declaredAnnotations = annotation.annotationType().getDeclaredAnnotations();
        for (Annotation declaredAnnotation : declaredAnnotations) {
            if (declaredAnnotation.annotationType().equals(annotationClass)) {
                return new AnnotationMate(annotation, declaredAnnotation);
            }
            AnnotationMate annotationMate = findAnnotationMate(declaredAnnotation, annotationClass);
            if( annotationMate != null){
                return annotationMate;
            }
        }
        return null;
    }

    @Data
    public static class AnnotationMate {
        /**
         * 被标注的注解上的注解
         */
        private Annotation implAnnotation;
        /**
         * 目标注解
         */
        private Annotation annotation;

        public AnnotationMate(Annotation implAnnotation, Annotation annotation) {
            this.implAnnotation = implAnnotation;
            this.annotation = annotation;
        }
    }
}
