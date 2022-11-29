package com.xwc.open.easy.parse.model;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：用于存储方法中的参数的各种信息 提供给后面的构建器来使用
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:23
 */
public abstract class ParameterAttribute {
    /**
     * 参数存在的位置
     */
    private int index;
    /**
     * 属性名称
     */
    private String parameterName;
    /**
     * 属性名称
     */
    private String column;
    /**
     * 属性路径 从根路径开始
     */
    private String[] path;

    /**
     * 方法上自带的注解
     */
    private final Map<Class<?>, Annotation> annotations = new HashMap<>();

    /**
     * 查找方法上是否有这个注解
     *
     * @param clazz 注解的Class文件
     * @return 返回包含的注解对象 当没有的时候返回 null
     */
    public Annotation findAnnotation(Class<? extends Annotation> clazz) {
        return annotations.get(clazz);
    }

    /**
     * 判断 方法上是否有这个注解
     *
     * @param clazz 注解的Class文件
     * @return 包含返回 true 不包含返回 false
     */
    public boolean containsAnnotation(Class<? extends Annotation> clazz) {
        return annotations.containsKey(clazz);
    }


    public void addAnnotation(Annotation annotation) {
        annotations.put(Annotation.class, annotation);
    }

    public void addAnnotations(List<Annotation> annotations) {
        annotations.forEach(annotation -> this.annotations.put(annotation.getClass(), annotation));
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }
}
