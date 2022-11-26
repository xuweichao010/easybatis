package com.xwc.open.easy.core.model;

import com.xwc.open.easy.core.assistant.DataBaseModelAssistant;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:16
 */
@SuppressWarnings("unused")
public class OperateMethodMeta {

    /**
     * 方法的名称
     */
    private String methodName;
    /**
     * 方法的参数信息
     */
    private final Set<ParameterAttribute> parameterAttributes = new HashSet<>();

    /**
     * 当需要进行填充属性的时候或者逻辑删除的时候，因为这些参数不会在方法上声明，需要把这些参数描述成虚拟的；
     */
    private final Set<ParameterAttribute> virtualParameterAttributes = new HashSet<>();
    /**
     * 方法生需要进行忽略的参数 被
     */
    private final Set<ParameterAttribute> ignoreParameterAttributes = new HashSet<>();

    /**
     * 方法上自带的注解
     */
    private Map<Class<?>, Annotation> annotations;

    /**
     * 这个方法和对应数据模型 需要根据数据模型获取一些信息 例如泛型的主键信息 泛型的数据模型构建对象参数信息
     */
    private DatabaseMeta databaseMeta;


    /**
     * 添加一个参数属性
     *
     * @param parameterAttribute 参数属性
     */
    public void addParameterAttribute(ParameterAttribute parameterAttribute) {
        this.parameterAttributes.add(parameterAttribute);
    }

    public void addIgnoreParameterAttribute(ParameterAttribute parameterAttribute) {
        this.ignoreParameterAttributes.add(parameterAttribute);
    }

    /**
     * 添加一个虚拟的参数属性
     *
     * @param parameterAttribute 参数属性
     */
    public void addVirtualParameterAttributes(ParameterAttribute parameterAttribute) {
        this.virtualParameterAttributes.add(parameterAttribute);
    }


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

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Set<ParameterAttribute> getParameterAttributes() {
        return parameterAttributes;
    }

    public Set<ParameterAttribute> getVirtualParameterAttributes() {
        return virtualParameterAttributes;
    }

    public Map<Class<?>, Annotation> getAnnotations() {
        return annotations;
    }

    public void setDatabaseMeta(DatabaseMeta databaseMeta) {
        this.databaseMeta = databaseMeta;
    }

    public Class<?> entityClass() {
        return databaseMeta.getSource();
    }

    public DatabaseMeta getDatabaseMeta() {
        return databaseMeta;
    }


}
