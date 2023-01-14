package com.xwc.open.easybatis.core;


import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.Condition;
import com.xwc.open.easybatis.core.anno.condition.filter.SetParam;
import com.xwc.open.easybatis.core.anno.table.*;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.excp.CheckEasyBatisException;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.*;
import com.xwc.open.easybatis.core.model.table.FieldFillMapping;
import com.xwc.open.easybatis.core.model.table.IdMapping;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import com.xwc.open.easybatis.core.model.table.Mapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.ParamNameUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建人：徐卫超 创建时间：2019/5/8  16:12 业务： 功能：用于获取注解信息
 */
public class AnnotationAssistant {

    private final EasybatisConfiguration configuration;
//    private final PlaceholderBuilder placeholderBuilder = new MyBatisPlaceholderBuilder();

    public AnnotationAssistant(EasybatisConfiguration configuration) {
        this.configuration = configuration;
    }


    public String tableName(Class<?> entityType) {
        Table table = AnnotationUtils.findAnnotation(entityType, Table.class);
        if (table == null) {
            throw new RuntimeException(entityType.getName() + "not find @Table Annotation");
        }
        String name = (String) AnnotationUtils.getValue(table, "value");
        if (StringUtils.isEmpty(name) && configuration.getTablePrefix() != null) {
            return configuration.getTablePrefix() + underscoreName(entityType.getSimpleName());
        }
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException(entityType.getName() + " not find @Table Annotation");
        }
        return name;
    }


    /**
     * 处理方法上的注解信息
     *
     * @param method method
     * @return
     */
    public MethodMeta parseMethodMate(Method method, TableMeta tableMetadata) {
        Annotation operationAnnotationType = chooseOperationAnnotationType(method);
        if (operationAnnotationType == null) {
            return null;
        }
        if (operationAnnotationType instanceof SelectSql) {
            return parseSelectMethodMate(method, tableMetadata);
        } else if (operationAnnotationType instanceof InsertSql) {
            return parseInsertMethodMate(method, tableMetadata);
        } else if (operationAnnotationType instanceof UpdateSql) {
            return parseUpdateMethodMate(method, tableMetadata);
        } else if (operationAnnotationType instanceof DeleteSql) {
            return parseDeleteMethodMate(method, tableMetadata);
        }
        return null;
    }

    public void parseMethodMate1(Method method, TableMeta tableMetadata) {
        Parameter[] parameters = method.getParameters();
        Parameter entityParameter = Stream.of(parameters)
                .filter(parameter -> {
                    if (parameter.getType().equals(tableMetadata.getClass())) {
                        return true;
                    }
                    if (parameter.getType().isAssignableFrom(Collection.class)) {
                        Class<?> entityClass = Reflection.geCollectionEntityClass(parameter.getType());
                        if (entityClass != null) {
                            return true;
                        }
                    }
                    return false;
                })
                .findAny().orElse(null);
        Parameter mapParameter = Stream.of(parameters).filter(parameter -> parameter.getType().isAssignableFrom(Map.class))
                .findAny().orElse(null);
        if (entityParameter != null && mapParameter != null) {
            throw new CheckEasyBatisException("参数中实体对象和Map无法共存");
        }


    }


    private MethodMeta parseUpdateMethodMate(Method method, TableMeta tableMetadata) {
        MethodMeta meta = new MethodMeta();
        meta.setOptionalAnnotationAttributes(AnnotationUtils.getAnnotationAttributes(AnnotationUtils.
                findAnnotation(method, UpdateSql.class)));
        meta.setTableMetadata(tableMetadata);
        meta.setMethodName(method.getName());
        meta.setSqlCommand(SqlCommandType.UPDATE);
        meta.setMethod(method);
        //解析方法
        List<ParamMate> paramList = new ArrayList<>();
        // 解析方法参数
        resolverMethodParams(meta, paramList);
        // 解析逻辑删除
        resolverLogic(meta, paramList);
        // 处理审计
        resolverFills(meta, paramList);
        // 解析参数
        resolverSqlParamSnippet(paramList, meta);
        // 处理更新条件
        ParamMate entityParam = paramList.stream()
                .filter(paramMate -> paramMate.getType() == ParamMate.TYPE_ENTITY).findAny().orElse(null);
        if (entityParam != null) {
            // 根据解析数据构建SQL条件
            // 添加主键修改条件
            IdMapping id = meta.getTableMetadata().getId();
            Placeholder placeholder = placeholderBuilder.parameterHolder(id.getField(), meta.isMulti() ? entityParam.getParamName() : null);
            meta.addParamMeta(ParamMapping.convert(id.getField(), id.getColumn(), placeholder, null, false,
                    ConditionType.EQUAL, null));
            LogicMapping logic = meta.getTableMetadata().getLogic();
            if (logic != null) {
                placeholder = placeholderBuilder.parameterHolder(logic.getField(), meta.isMulti() ? entityParam.getParamName() : null);
                meta.addParamMeta(ParamMapping.convert(logic.getField(), logic.getColumn(),
                        placeholder, null, false, ConditionType.EQUAL, null));
            }
        }
        return meta;
    }

    private MethodMeta parseDeleteMethodMate(Method method, TableMeta tableMetadata) {

    }

    public MethodMeta parseSelectMethodMate(Method method, TableMeta tableMetadata) {

        return meta;
    }

    private MethodMeta parseInsertMethodMate(Method method, TableMeta tableMetadata) {

        return meta;
    }

    private void resolverSqlParamSnippet(List<ParamMate> paramList, MethodMeta methodMeta) {

        methodMeta.setParamMetaList(list);

    }

    private List<ParamMapping> parseObjectMate(ParamMate paramMate, boolean isMulti, boolean methodDynamic) {
        return paramMate.getChildren().stream().map(item -> {
            if (isMulti) {
                return parseParamMate(item, paramMate.getParamName(), false, methodDynamic, true);
            } else {
                return parseParamMate(item, null, false, methodDynamic, true);
            }
        }).collect(Collectors.toList());
    }

    private ParamMapping parseParamMate(ParamMate paramMate, String parentName,
                                        boolean isMulti, boolean methodDynamic, boolean isObject) {
        String methodParamName = StringUtils.hasText(parentName) ? parentName : paramMate.getParamName();
        String paramName = paramMate.getParamName();
        if (paramMate.getType() == ParamMate.TYPE_AUDITOR) {
            return ParamMapping.convert(paramName, underscoreName(paramName),
                    builderPlaceholder(paramName, parentName), null, false, ConditionType.SET_PARAM, null);
        }
        if (paramMate.getType() == ParamMate.TYPE_LOGIC) {
            return ParamMapping.convert(paramName, underscoreName(paramName),
                    builderPlaceholder(paramName, parentName), null, false, ConditionType.EQUAL, null);
        }

        if (paramMate.getAnnotation() == null) {
            return ParamMapping.convert(paramName, underscoreName(paramName),
                    builderPlaceholder(paramName, parentName), null, methodDynamic, ConditionType.EQUAL, methodParamName);
        }

        // 获取参数条件
        AnnotationUtils.AnnotationMate annotationMate = AnnotationUtils.findAnnotationMate(paramMate.getAnnotation(), Condition.class);
        if (annotationMate == null) {
            throw new IllegalArgumentException("非法的注解信息");
        }
        Condition annotation = (Condition) annotationMate.getAnnotation();

        // 获取参数信息
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(paramMate.getAnnotation());
        String alias = (String) annotationAttributes.get("alias");
        boolean dynamic = (annotationAttributes.get("dynamic") != null && (boolean) annotationAttributes.get("dynamic"));
        String value = (String) annotationAttributes.get("value");

        if (annotation.type() == ConditionType.IGNORE) {
            return ParamMapping.convert(paramName, null, null,
                    null, false, annotation.type(), methodParamName);
        }
        // 处理方法参数中只有一个IN 查询的时候
        if (!isMulti && (annotation.type() == ConditionType.IN || annotation.type() == ConditionType.NOT_IN) && !isObject) {
            return ParamMapping.convert("collection",
                    StringUtils.hasText(value) ? value : underscoreName(paramName),
                    builderPlaceholder("collection", parentName),
                    alias, methodDynamic || dynamic, annotation.type(), methodParamName);
        }
        return ParamMapping.convert(paramName, StringUtils.hasText(value) ? value : underscoreName(paramName), builderPlaceholder(paramName, parentName),
                alias, dynamic || methodDynamic, annotation.type(), methodParamName);
    }


    private Placeholder builderPlaceholder(String paramName, String parentName) {
        return placeholderBuilder.parameterHolder(paramName, parentName);
    }


    /**
     * 解析方法上的参数
     *
     * @param methodMeta 元信息
     * @param paramList  结果集合
     */
    private void resolverMethodParams(MethodMeta methodMeta, List<ParamMate> paramList) {
        Parameter[] parameters = methodMeta.getMethod().getParameters();
        List<String> paramNames = ParamNameUtil.getParamNames(methodMeta.getMethod());
        for (int i = 0; i < paramNames.size(); i++) {
            ParamMate paramMeta = parseParameter(parameters[i], paramNames.get(i), methodMeta);
            paramList.add(paramMeta);
        }
    }


    private ParamMate parseParameter(Parameter parameter, String paramName, MethodMeta methodMeta) {
        Class<?> entityClass = methodMeta.getTableMetadata().getSource();
        //处理接口泛型 泛型的两种情况是 主键或者是实体
        ParamMate paramMeta;
        if (parameter.getParameterizedType() instanceof TypeVariable) {
            if (isEntityParam(parameter.getParameterizedType(), entityClass)) {
                if (SqlCommandType.INSERT == methodMeta.getSqlCommand() || SqlCommandType.UPDATE == methodMeta.getSqlCommand()) {
                    return ParamMate.builder(paramName, ParamMate.TYPE_ENTITY);
                } else {
                    return parseCustomParameter(entityClass, paramName);
                }
            } else if (isKeyParam(parameter.getParameterizedType())) {
                paramMeta = ParamMate.builder(methodMeta.getTableMetadata().getId().getField(), ParamMate.TYPE_KEY);
            } else {
                throw new EasyBatisException("泛型类型不匹配");
            }
            return paramMeta;
        } else if (parameter.getParameterizedType() instanceof ParameterizedType) { // 处理接口中的集合类型
            ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
            Annotation annotation = chooseQueryAnnotationType(parameter.getAnnotations());
            if (Collection.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
                if (methodMeta.getSqlCommand() == SqlCommandType.SELECT) {
                    return ParamMate.builder(paramName, annotation);
                } else if (isEntityParam(parameterizedType.getActualTypeArguments()[0], entityClass)
                        && (SqlCommandType.INSERT == methodMeta.getSqlCommand() || SqlCommandType.UPDATE == methodMeta.getSqlCommand())) {
                    return ParamMate.builderBatch(paramName, ParamMate.TYPE_ENTITY);
                } else if (isKeyParam(parameterizedType.getActualTypeArguments()[0])) {
                    return ParamMate.builder(paramName, ParamMate.TYPE_KEY, annotation);
                } else {
                    return ParamMate.builder(paramName, annotation);
                }
            } else {
                return ParamMate.builder(paramName, annotation);
            }
        } else {
            if (isEntityParam(parameter.getParameterizedType(), entityClass)) {
                paramMeta = ParamMate.builder(paramName, ParamMate.TYPE_ENTITY);
            } else if (Reflection.isCustomObject(parameter.getType()) && methodMeta.getSqlCommand() == SqlCommandType.SELECT) {
                paramMeta = parseCustomParameter((Class<?>) parameter.getParameterizedType(), paramName);
            } else {
                Annotation annotation = chooseQueryAnnotationType(parameter.getAnnotations());
                paramMeta = ParamMate.builder(paramName, annotation);
            }
            return paramMeta;
        }

    }

    private ParamMate parseCustomParameter(Class<?> type, String paramName) {
        ParamMate param = ParamMate.builder(paramName, ParamMate.TYPE_CUSTOM_ENTITY);
        List<Field> fieldList = Reflection.getField(type);
        List<ParamMate> paramList = fieldList.stream()
                .map(field ->
                        ParamMate.builder(field.getName(), chooseQueryAnnotationType(field.getDeclaredAnnotations())))
                .collect(Collectors.toList());
        param.setChildren(paramList);
        return param;
    }

    /**
     * 解析审计信息
     *
     * @param methodMeta 元信息
     * @param paramList  结果集合
     */
    private void resolverFills(MethodMeta methodMeta, List<ParamMate> paramList) {
        if (paramList.stream().noneMatch(paramMate -> paramMate.getType() == ParamMate.TYPE_ENTITY)
                && SqlCommandType.UPDATE == methodMeta.getSqlCommand()) {
            if (paramList.stream().noneMatch(paramMate -> paramMate.getType() == ParamMate.TYPE_ENTITY)) {
                methodMeta.getTableMetadata().getFieldFills().stream().filter(item -> item.getType().command() == SqlCommandType.UPDATE).forEach(auditorMapping -> {
                    paramList.add(ParamMate.builder(auditorMapping.getField(), ParamMate.TYPE_AUDITOR,
                            chooseAuditsAnnotationType(methodMeta.getMethod());
                });
            }
        }
    }


    /**
     * 解析逻辑删除信息
     *
     * @param methodMeta 元信息
     * @param paramList  结果集合
     */
    private void resolverLogic(MethodMeta methodMeta, List<ParamMate> paramList) {
        LogicMapping logic = methodMeta.getTableMetadata().getLogic();
        if (logic == null) return;
        if (methodMeta.getSqlCommand() != SqlCommandType.INSERT) {
            if (paramList.stream().noneMatch(paramMate -> paramMate.getType() == ParamMate.TYPE_ENTITY)) {
                paramList.add(ParamMate.builder(logic.getColumn(), ParamMate.TYPE_LOGIC));
            }
        }
    }


    private boolean isEntityParam(Type parameterType, Class<?> entityClass) {
        return parameterType.getTypeName().equals("E") || parameterType.equals(entityClass);
    }

    private boolean isKeyParam(Type entityType) {
        return entityType.getTypeName().equals("K");
    }


    /**
     * 解析字段的注解信息
     *
     * @param field 字段
     * @param clazz 实体的class对象
     * @return 字段和数据表的字段映射关系 如果存在关系则返回Column 负责返回空
     */
    private Mapping analysisColunm(Field field, Class<?> clazz) {
        Method getter;
        Method setter;
        try {
            getter = Reflection.getter(field, clazz);
            setter = Reflection.setter(field, clazz);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return Mapping.builder(field, getter, setter, underscoreName(field.getName()));
    }


    /**
     * 判断是否是忽略字段
     */
    private boolean isIgnore(Field field) {
        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
    }


    /**
     * 集合中是否有填充属性
     *
     * @param method
     * @return
     */
    public Annotation chooseAuditsAnnotationType(Method method) {
        return AnnotationUtils.findAnnotation(method, Logic.class);
    }

//    /**
//     * 判断注解数组中是否有查询条件注解
//     */
//    private Annotation chooseQueryAnnotationType(Method method) {
//        return AnnotationUtils.findAnnotation(method, FieldFill.class);
//    }

    /**
     * 判断注解数组中是否有查询条件注解
     */
    private Annotation chooseSetParamAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (SetParam.class.equals(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 判断注解数组中是否有查询条件注解
     */
    private <T> Annotation chooseAnnotationType(Annotation[] annotations, Class<T> annotationClass) {
        for (Annotation annotation : annotations) {
            if (annotationClass.equals(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 判断注解数组中是否有查询条件注解
     */
    public Annotation chooseOperationAnnotationType(Method method) {
        Annotation[] annotations = method.getAnnotations();
        return chooseOperationAnnotationType(annotations);
    }

    public Annotation chooseOperationAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (operationAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }


    private String underscoreName(String camelCaseName) {
        if (configuration.isMapUnderscoreToCamelCase()) {

        }
        return camelCaseName;
    }
}
