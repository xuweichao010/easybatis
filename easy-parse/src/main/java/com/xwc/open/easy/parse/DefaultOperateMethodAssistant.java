package com.xwc.open.easy.parse;

import com.xwc.open.easy.parse.annotations.Ignore;
import com.xwc.open.easy.parse.annotations.Syntax;
import com.xwc.open.easy.parse.exceptions.CheckDatabaseModelException;
import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.model.ParameterAttribute;
import com.xwc.open.easy.parse.model.TableMeta;
import com.xwc.open.easy.parse.model.parameter.*;
import com.xwc.open.easy.parse.supports.EasyMapper;
import com.xwc.open.easy.parse.utils.AnnotationUtils;
import com.xwc.open.easy.parse.utils.ParamNameUtil;
import com.xwc.open.easy.parse.utils.Reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 类描述：默认的属性类型
 * 作者：徐卫超 (cc)
 * 时间 2022/11/26 21:01
 */
public class DefaultOperateMethodAssistant implements OperateMethodAssistant {
    private final EasyConfiguration configuration;

    public DefaultOperateMethodAssistant(EasyConfiguration easyConfiguration) {
        this.configuration = easyConfiguration;
    }

    @Override
    public OperateMethodMeta getOperateMethodMeta(Class<?> clazz, Method method) {
        if (clazz == null || method == null || !EasyMapper.class.isAssignableFrom(clazz)) {
            return null;
        }
        OperateMethodMeta methodMeta = getMethodMeta(method);
        TableMeta entityClass = configuration.getTableMetaAssistant().getTableMeta(Reflection.getEntityClass(clazz));
        methodMeta.setDatabaseMeta(entityClass);
        parseMethod(method, methodMeta);
        return methodMeta;
    }


    private void parseMethod(Method method, OperateMethodMeta methodMeta) {
        Parameter[] parameters = method.getParameters();
        List<String> paramNames = ParamNameUtil.getParamNames(method);
        for (int i = 0; i < paramNames.size(); i++) {
            ParameterAttribute parameterAttributes = parseParameter(parameters[i], paramNames.get(i), methodMeta);
            if (parameterAttributes.containsAnnotation(Ignore.class)) {
                methodMeta.addIgnoreParameterAttribute(parameterAttributes);
            } else {
                methodMeta.addParameterAttribute(parameterAttributes);
            }
        }
    }

    private ParameterAttribute parseParameter(Parameter parameter, String parameterName, OperateMethodMeta methodMeta) {
        if (parameter.getParameterizedType() instanceof TypeVariable) { // 处理泛型
            if (isEntityParam(parameter.getParameterizedType(), methodMeta.getDatabaseMeta().getSource())) {
                return parameterAttribute(new EntityParameterAttribute(methodMeta.getDatabaseMeta()), parameter, parameterName);
            } else if (isKeyParam(parameter.getParameterizedType())) {
                return parameterAttribute(new PrimaryKeyParameterAttribute(methodMeta.getDatabaseMeta().getPrimaryKey()),
                        parameter, parameterName);
            } else {
                throw new CheckDatabaseModelException("泛型类型不匹配");
            }
        } else if (parameter.getParameterizedType() instanceof ParameterizedType) { //处理集合 Map、Collection
            ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
            if (Collection.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                if (isEntityParam(parameterizedType.getActualTypeArguments()[0], methodMeta.getDatabaseMeta().getSource())) {
                    return parameterAttribute(new CollectionEntityParameterAttribute(methodMeta.getDatabaseMeta()),
                            parameter, parameterName);
                } else if (isKeyParam(parameterizedType.getActualTypeArguments()[0])) {
                    return parameterAttribute(new CollectionPrimaryKeyParameterAttribute(methodMeta.getDatabaseMeta().getPrimaryKey()),
                            parameter, parameterName);
                } else {
                    // 这里需不需要进行类型处理 有可能是自定义对象 没有想到比较好的场景来需要自定义对象数组集合来处理数据,因为泛型实体已经满足了必要的需求
                    return parameterAttribute(new CollectionParameterAttribute(), parameter, parameterName);
                }
            } else {

                return parameterAttribute(new MapParameterAttribute(methodMeta.getDatabaseMeta().getPrimaryKey()), parameter, parameterName);
            }
        } else {
            if (isEntityParam(parameter.getParameterizedType(), methodMeta.getDatabaseMeta().getSource())) {
                return parameterAttribute(new EntityParameterAttribute(methodMeta.getDatabaseMeta()), parameter, parameterName);
            } else if (Reflection.isCustomObject(parameter.getType())) {
                return parameterAttribute(new ObjectParameterAttribute(parameter.getType()), parameter, parameterName);
            } else {
                return parameterAttribute(new BaseParameterAttribute(), parameter, parameterName);
            }

        }
    }

    private ParameterAttribute parameterAttribute(ParameterAttribute attribute, Parameter parameter, String parameterName) {
        if (parameter != null) {
            attribute.addAnnotations(registerAnnotation(parameter.getAnnotations()));
        }
        attribute.setParameterName(parameterName);
        attribute.setPath(Collections.singletonList(parameterName).toArray(new String[]{}));
        return attribute;
    }


    public OperateMethodMeta getMethodMeta(Method method) {
        OperateMethodMeta methodMeta = new OperateMethodMeta();
        methodMeta.setMethodName(method.getName());
        //缓存和系统有关的所有注解
        methodMeta.addAnnotations(registerAnnotation(method.getDeclaredAnnotations()));
        return methodMeta;
    }

    /**
     * 识别和系统中所有有关的注解 这些注解都引用 @link com.xwc.open.easy.core.annotations.Syntax 方法
     *
     * @param annotations 需要识别的注解
     * @return 返回满足要求注解
     */
    private List<Annotation> registerAnnotation(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> AnnotationUtils.findAnnotation(annotation.getClass(), Syntax.class) != null)
                .collect(Collectors.toList());
    }

    /**
     * 判断类型是否为实体类型
     *
     * @param parameterType 参数类型
     * @param entityClass   实体类型
     * @return
     */
    private boolean isEntityParam(Type parameterType, Class<?> entityClass) {
        return parameterType.getTypeName().equals("E") || parameterType.equals(entityClass);
    }

    private boolean isKeyParam(Type entityType) {
        return entityType.getTypeName().equals("K");
    }

}
