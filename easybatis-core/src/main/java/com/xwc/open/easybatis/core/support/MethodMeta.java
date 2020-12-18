package com.xwc.open.easybatis.core.support;

import lombok.Data;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
public class MethodMeta {

    private String methodName;

    /**
     * SQL操作的类型
     */
    private SqlCommandType sqlCommond;

    private boolean dynamic;
    /**
     * 关联的表的实体的定义信息
     */
    TableMeta tableMetadata;
    /**
     * 方法上的注解定义信息
     */
    Set<Annotation> methodAnnotation = new HashSet<>();

    /**
     * 参数有的定义信息
     */
    List<ParamMeta> paramMetaList;


    public void addAnnotation(Annotation annotation) {
        if (annotation != null) {
            this.methodAnnotation.add(annotation);
        }
    }

    public boolean hashAnnotation(Class<?> annotationClass) {
        if (annotationClass == null) {
            return false;
        }
        for (Annotation ann : methodAnnotation) {
            if (annotationClass.equals(ann.annotationType())) {
                return true;
            }
        }
        return false;
    }


    public <T extends Annotation> T findAnnotation(Class<T> annotationClass) {
        if (annotationClass == null) {
            return null;
        }
        for (Annotation ann : methodAnnotation) {
            if (annotationClass.equals(ann.annotationType())) {
                return (T) ann;
            }
        }
        return null;
    }


}
