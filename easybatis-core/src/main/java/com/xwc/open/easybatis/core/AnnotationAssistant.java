package com.xwc.open.easybatis.core;


import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.anno.table.*;
import com.xwc.open.easybatis.core.anno.table.fill.*;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.*;
import com.xwc.open.easybatis.core.model.table.FieldFillMapping;
import com.xwc.open.easybatis.core.model.table.IdMapping;
import com.xwc.open.easybatis.core.model.table.LogicMapping;
import com.xwc.open.easybatis.core.model.table.Mapping;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.impl.MyBatisPlaceholderBuilder;
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
    private final PlaceholderBuilder placeholderBuilder = new MyBatisPlaceholderBuilder();

    public AnnotationAssistant(EasybatisConfiguration configuration) {
        this.configuration = configuration;
    }

    private final static Set<Class<? extends Annotation>> fillAnnoSet = Stream.of(CreateId.class,
            UpdateId.class, CreateName.class, UpdateName.class, CreateTime.class, UpdateTime.class, FieldFill.class)
            .collect(Collectors.toSet());
    private final static Set<Class<? extends Annotation>> operationAnnoSet = Stream
            .of(SelectSql.class, InsertSql.class, UpdateSql.class, DeleteSql.class).collect(Collectors.toSet());
    private static final Set<Class<? extends Annotation>> queryAnnoSet = Stream
            .of(Equal.class, NotEqual.class, IsNull.class, IsNotNull.class, In.class, NotIn.class,
                    Like.class, RightLike.class, LeftLike.class, NotLike.class, NotLeftLike.class, NotRightLike.class,
                    GreaterThan.class, GreaterThanEqual.class, LessThan.class
                    , LessThanEqual.class, Start.class, Offset.class, ASC.class, DESC.class, Ignore.class, SetParam.class)
            .collect(Collectors.toSet());

    public String tableName(Class<?> entityType) {
        Table tableAnno = AnnotationUtils.findAnnotation(entityType, Table.class);
        if (tableAnno == null) {
            throw new RuntimeException(entityType.getName() + "not find @Table Annotation");
        }
        String name = (String) AnnotationUtils.getValue(tableAnno, "value");
        if (StringUtils.isEmpty(name) && configuration.getTablePrefix() != null) {
            return configuration.getTablePrefix() + underscoreName(entityType.getSimpleName());
        }
        if (StringUtils.isEmpty(name)) {
            throw new RuntimeException(entityType.getName() + " not find @Table Annotation");
        }
        return name;
    }

    /**
     * 解析实体信息
     *
     * @param entityType 实体Class对象
     * @return 返回TableMetadata
     */
    public TableMeta parseEntityMate(Class<?> entityType) {
        TableMeta table = new TableMeta();
        table.setTableName(tableName(entityType));
        table.setSource(entityType);
        List<Field> fieldArr = Reflection.getField(entityType);
        for (Field field : fieldArr) {
            if (isIgnore(field)) {
                continue;
            }
            Mapping mapping = analysisColunm(field, entityType);
            if (mapping == null) {
                continue;
            }
            if (mapping.hashAnnotationType(Id.class)) {
                Id id = mapping.chooseAnnotationType(Id.class);
                table.setId(new IdMapping(mapping,
                        id.type() == IdType.GLOBAL ? configuration.useGlobalPrimaKeyType() : id.type(), id));
                continue;
            } else if (mapping.hashAnnotationType(Logic.class)) {
                Logic loglic = mapping.chooseAnnotationType(Logic.class);
                table.setLogic(new LogicMapping(mapping, loglic));
                continue;
            } else if (mapping.hashAnnotationType(FieldFill.class)) {
                AnnotationUtils.AnnotationMate mate = AnnotationUtils.findAnnotationMate(field, FieldFill.class);
                mapping.mergeAnnotationAttributes(AnnotationUtils.getAnnotationAttributes(mate.getImplAnnotation()));
                FieldFill fieldFill = (FieldFill) mate.getAnnotation();
                table.addAuditor(new FieldFillMapping(mapping, fieldFill.attribute(), fieldFill.type()));
                continue;
            } else if (mapping.hashAnnotationType(Column.class)) {
                Column column = mapping.chooseAnnotationType(Column.class);
                mapping.mergeAnnotationAttributes(AnnotationUtils.getAnnotationAttributes(column));
            }
            table.addColumn(mapping);
        }
        return table.validate();
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
        resolverfills(meta, paramList);
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
        MethodMeta meta = new MethodMeta();
        meta.setTableMetadata(tableMetadata);
        meta.setOptionalAnnotationAttributes(AnnotationUtils.getAnnotationAttributes(AnnotationUtils.
                findAnnotation(method, DeleteSql.class)));
        meta.setMethodName(method.getName());
        meta.setMethod(method);
        LogicMapping logic = meta.getTableMetadata().getLogic();
        if (logic == null) {
            meta.setSqlCommand(SqlCommandType.DELETE);
        } else {
            meta.setSqlCommand(SqlCommandType.UPDATE);
        }
        //解析方法
        List<ParamMate> paramList = new ArrayList<>();
        // 解析方法参数
        resolverMethodParams(meta, paramList);
        // 解析逻辑删除
        resolverLogic(meta, paramList);
        // 处理审计
        resolverfills(meta, paramList);
        // 解析参数
        resolverSqlParamSnippet(paramList, meta);
        //有逻辑删除时
        if (logic != null) {
            meta.setLogicallyDelete(true);
            Placeholder placeholder = placeholderBuilder.parameterHolder(logic.getField(), null);
            meta.addParamMeta(ParamMapping.convert(logic.getField() + "0", logic.getColumn(), placeholder,
                    null, false, ConditionType.SET_PARAM, null));
        }
        return meta;
    }

    public MethodMeta parseSelectMethodMate(Method method, TableMeta tableMetadata) {
        MethodMeta meta = new MethodMeta();
        meta.setOptionalAnnotationAttributes(AnnotationUtils.getAnnotationAttributes(AnnotationUtils.
                findAnnotation(method, SelectSql.class)));
        meta.setTableMetadata(tableMetadata);
        meta.setMethodName(method.getName());
        meta.setSqlCommand(SqlCommandType.SELECT);
        meta.setMethod(method);
        List<ParamMate> paramList = new ArrayList<>();
        // 解析方法参数
        resolverMethodParams(meta, paramList);
        // 解析逻辑删除
        resolverLogic(meta, paramList);
        // 根据解析数据构建SQL条件
        resolverSqlParamSnippet(paramList, meta);
        return meta;
    }

    private MethodMeta parseInsertMethodMate(Method method, TableMeta tableMetadata) {
        MethodMeta meta = new MethodMeta();
        meta.setOptionalAnnotationAttributes(AnnotationUtils.getAnnotationAttributes(AnnotationUtils.
                findAnnotation(method, InsertSql.class)));
        meta.setTableMetadata(tableMetadata);
        meta.setMethodName(method.getName());
        meta.setSqlCommand(SqlCommandType.INSERT);
        meta.setMethod(method);
        List<ParamMate> paramList = new ArrayList<>();
        // 解析方法参数
        resolverMethodParams(meta, paramList);
        // 解析审计
        resolverfills(meta, paramList);
        // 解析参数
        resolverSqlParamSnippet(paramList, meta);
        return meta;
    }

    private void resolverSqlParamSnippet(List<ParamMate> paramList, MethodMeta methodMeta) {
        List<ParamMapping> list = new ArrayList<>();
        if (paramList.isEmpty()) {
            methodMeta.setParamMetaList(list);
        }
        boolean isMulti = paramList.size() > 1;
        methodMeta.setMulti(isMulti);
        // 方法是否是动态
        boolean methodDynamic = methodMeta.optionalBooleanAttributes("dynamic");
        paramList.forEach(paramMate -> {
            if (paramMate.getType() == ParamMate.TYPE_CUSTOM_ENTITY) {
                list.addAll(parseObjectMate(paramMate, isMulti, true));
            } else if (paramMate.getType() == ParamMate.TYPE_ENTITY) {
                if (methodMeta.getSqlCommand() == SqlCommandType.INSERT || methodMeta.getSqlCommand() == SqlCommandType.UPDATE) {
                    list.add(ParamMapping.convertEntity(paramMate.getParamName(), paramMate.isBatch()));
                } else {
                    list.addAll(parseObjectMate(paramMate, isMulti, true));
                }
            } else {
                ParamMapping paramMapping = parseParamMate(paramMate, null, isMulti, methodDynamic, false);
                if (paramMapping != null) {
                    list.add(paramMapping);
                }
            }
        });
        methodMeta.setParamMetaList(list);

    }

    private List<ParamMapping> parseObjectMate(ParamMate paramMate, boolean isMulti, boolean methodDynamic) {
        return paramMate.getChildren().stream().map(item -> {
            if (isMulti) {
                return parseParamMate(item, paramMate.getParamName(), false, methodDynamic, true);
            } else {
                return parseParamMate(item, null, false, methodDynamic, true);
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
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
                } else {
                    throw new EasyBatisException("Easybatis 框架解析错误");
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
    private void resolverfills(MethodMeta methodMeta, List<ParamMate> paramList) {
        if (paramList.stream().noneMatch(paramMate -> paramMate.getType() == ParamMate.TYPE_ENTITY)
                && SqlCommandType.UPDATE == methodMeta.getSqlCommand()) {
            if (paramList.stream().noneMatch(paramMate -> paramMate.getType() == ParamMate.TYPE_ENTITY)) {
                methodMeta.getTableMetadata().getFieldFills().stream().filter(item -> item.getType().command() == SqlCommandType.UPDATE).forEach(auditorMapping -> {
                    paramList.add(ParamMate.builder(auditorMapping.getField(), ParamMate.TYPE_AUDITOR,
                            chooseAuditsAnnotationType(auditorMapping.getAnnotationSet())));
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


    private boolean isEntityParam(Type entityType, Class<?> entityClass) {
        return entityType.getTypeName().equals("E") || entityType.equals(entityClass);
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
     * @param annotationSet
     * @return
     */
    public Annotation chooseAuditsAnnotationType(Set<Annotation> annotationSet) {
        for (Annotation annotation : annotationSet) {
            if (fillAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 判断注解数组中是否有查询条件注解
     */
    private Annotation chooseQueryAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (queryAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

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
            StringBuilder result = new StringBuilder();
            if (camelCaseName != null && camelCaseName.length() > 0) {
                result.append(camelCaseName.substring(0, 1).toLowerCase());
                for (int i = 1; i < camelCaseName.length(); i++) {
                    char ch = camelCaseName.charAt(i);
                    if (Character.isUpperCase(ch)) {
                        result.append("_");
                        result.append(Character.toLowerCase(ch));
                    } else {
                        result.append(ch);
                    }
                }
            }
            return result.toString();
        }
        return camelCaseName;
    }
}
