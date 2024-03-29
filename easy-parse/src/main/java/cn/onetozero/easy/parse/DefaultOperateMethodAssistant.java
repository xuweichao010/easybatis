package cn.onetozero.easy.parse;

import cn.onetozero.easy.parse.annotations.Ignore;
import cn.onetozero.easy.parse.annotations.Syntax;
import cn.onetozero.easy.parse.exceptions.CheckDatabaseModelException;
import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.model.ParameterAttribute;
import cn.onetozero.easy.parse.model.TableMeta;
import cn.onetozero.easy.parse.model.parameter.*;
import cn.onetozero.easy.parse.supports.EasyMapper;
import cn.onetozero.easy.parse.utils.AnnotationUtils;
import cn.onetozero.easy.parse.utils.ParamNameUtil;
import cn.onetozero.easy.parse.utils.Reflection;

import java.lang.reflect.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 类描述：默认的属性类型
 * 作者：徐卫超 (cc)
 * 时间 2022/11/26 21:01
 */
public class DefaultOperateMethodAssistant implements OperateMethodAssistant {
    private final EasyConfiguration configuration;

    private Map<String, OperateMethodMeta> operateMethodMetaMap = new ConcurrentHashMap<>();

    public DefaultOperateMethodAssistant(EasyConfiguration easyConfiguration) {
        this.configuration = easyConfiguration;
    }

    @Override
    public OperateMethodMeta getOperateMethodMeta(Class<?> clazz, Method method) {
        if (clazz == null || method == null || !EasyMapper.class.isAssignableFrom(clazz)) {
            return null;
        }
        return operateMethodMetaMap.computeIfAbsent(clazz.getName() + "." + method.getName(), val -> {
            OperateMethodMeta methodMeta = getMethodMeta(method);
            TableMeta entityClass = configuration.getTableMetaAssistant().getTableMeta(Reflection.getEntityClass(clazz));
            methodMeta.setDatabaseMeta(entityClass);
            parseMethod(method, methodMeta);
            return methodMeta;
        });
    }

    /**
     * 解析方法上的元信息
     *
     * @param method     方法对象
     * @param methodMeta 方法的元信息
     */
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

    /**
     * 解析参数类型所属类型 该类型 输入 ParamMate类型的子类
     *
     * @param parameter     参数
     * @param parameterName 参数名称
     * @param methodMeta    方法的元信息
     * @return 返回一个参数类型
     */
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
            attribute.addAnnotations(AnnotationUtils.registerAnnotation(parameter.getAnnotations(), Syntax.class));
        }
        attribute.setParameterName(parameterName);
        attribute.setPath(Collections.singletonList(parameterName).toArray(new String[]{}));
        return attribute;
    }


    public OperateMethodMeta getMethodMeta(Method method) {
        OperateMethodMeta methodMeta = new OperateMethodMeta();
        methodMeta.setMethodName(method.getName());
        //缓存和系统有关的所有注解
        methodMeta.addAnnotations(AnnotationUtils.registerAnnotation(method.getDeclaredAnnotations(), Syntax.class));
        return methodMeta;
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
