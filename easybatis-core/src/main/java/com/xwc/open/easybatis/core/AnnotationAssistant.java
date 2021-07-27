package com.xwc.open.easybatis.core;


import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.anno.table.*;
import com.xwc.open.easybatis.core.anno.table.auditor.*;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.*;
import com.xwc.open.easybatis.core.model.table.AuditorMapping;
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

    private final static Set<Class<? extends Annotation>> AuditorAnnoSet = Stream.of(CreateId.class,
            UpdateId.class, CreateName.class, UpdateName.class, CreateTime.class, UpdateTime.class, Auditor.class)
            .collect(Collectors.toSet());
    private final static Set<Class<? extends Annotation>> operationAnnoSet = Stream
            .of(SelectSql.class, InsertSql.class, UpdateSql.class, DeleteSql.class).collect(Collectors.toSet());
    private static final Set<Class<? extends Annotation>> queryAnnoSet = Stream
            .of(Equal.class, NotEqual.class, IsNull.class, IsNotNull.class, In.class, NotIn.class,
                    Like.class, RightLike.class, LeftLike.class, NotLike.class, NotLeftLike.class, NotRightLike.class,
                    GreaterThan.class, GreaterThanEqual.class, LessThan.class
                    , LessThanEqual.class, Start.class, Offset.class, ASC.class, DESC.class, Ignore.class)
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
            Mapping columnMeta = analysisColunm(field, entityType);
            if (columnMeta == null) {
                continue;
            }
            if (columnMeta.hashAnnotationType(Id.class)) {
                Id id = columnMeta.chooseAnnotationType(Id.class);
                table.setId(new IdMapping(columnMeta,
                        id.type() == IdType.GLOBAL ? configuration.useGlobalPrimaKeyType() : id.type(), id));
                continue;
            } else if (columnMeta.hashAnnotationType(Logic.class)) {
                Logic loglic = columnMeta.chooseAnnotationType(Logic.class);
                table.setLogic(new LogicMapping(columnMeta, loglic));
                continue;
            } else if (columnMeta.hashAnnotationType(Auditor.class)) {
                AnnotationUtils.AnnotationMate mate = AnnotationUtils.findAnnotationMate(field, Auditor.class);
                columnMeta.mergeTableAnnotation(AnnotationUtils.getAnnotationAttributes(mate.getImplAnnotation()));
                table.addAuditor(new AuditorMapping(columnMeta, ((Auditor) mate.getAnnotation()).type()));

                continue;
            } else if (columnMeta.hashAnnotationType(Column.class)) {
                Column column = columnMeta.chooseAnnotationType(Column.class);
                columnMeta.mergeTableAnnotation(AnnotationUtils.getAnnotationAttributes(column));
            }
            table.addColumn(columnMeta);
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
        }
//        else if (operationAnnotationType instanceof InsertSql) {
//            return parseInsertMethodMate(method, tableMetadata);
//        } else if (operationAnnotationType instanceof UpdateSql) {
//            return parseUpdateMethodMate(method, tableMetadata);
//        } else if (operationAnnotationType instanceof DeleteSql) {
//            return parseDeleteMethodMate(method, tableMetadata);
//        }
        return null;
    }

//    private MethodMeta parseInsertMethodMate(Method method, TableMeta tableMetadata) {
//        MethodMeta meta = new MethodMeta();
//        meta.setDynamic(false);
//        meta.setTableMetadata(tableMetadata);
//        meta.setMethodName(method.getName());
//        meta.setSqlCommand(SqlCommandType.INSERT);
//        meta.setMethod(method);
//        meta.setParamMetaList(parseMethodParam(meta));
//        return meta;
//    }
//
//    private MethodMeta parseUpdateMethodMate(Method method, TableMeta tableMetadata) {
//        MethodMeta meta = new MethodMeta();
//        meta.setDynamic(AnnotationUtils.findAnnotation(method, UpdateSql.class).dynamic());
//        meta.setTableMetadata(tableMetadata);
//        meta.setMethodName(method.getName());
//        meta.setSqlCommand(SqlCommandType.UPDATE);
//        meta.setMethod(method);
//        meta.setParamMetaList(parseMethodParam(meta));
//        List<ParamMapping> list = meta.getParamMetaList().stream().filter(ParamMapping::isList).collect(Collectors.toList());
//        if (!list.isEmpty()) {
//            throw new EasyBatisException("无法处理批量跟新数据");
//        }
//        return meta;
//    }

//    private MethodMeta parseDeleteMethodMate(Method method, TableMeta tableMetadata) {
//        MethodMeta meta = new MethodMeta();
//        meta.setTableMetadata(tableMetadata);
//        meta.setMethodName(method.getName());
//        meta.setSqlCommand(SqlCommandType.DELETE);
//        meta.setMethod(method);
//        parseMethodParam1(meta);
//        return meta;
//    }

    public MethodMeta parseSelectMethodMate(Method method, TableMeta tableMetadata) {
        MethodMeta meta = new MethodMeta();
        meta.setOptionalAnnotationAttributes(AnnotationUtils.getAnnotationAttributes(AnnotationUtils.findAnnotation(method, SelectSql.class)));
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

    private void resolverSqlParamSnippet(List<ParamMate> paramList, MethodMeta methodMeta) {
        List<ParamMapping> list = new ArrayList<>();
        if (paramList.isEmpty()) {
            methodMeta.setParamMetaList(list);
        }
        boolean isMulti = paramList.size() > 1;
        // 方法是否是动态
        boolean methodDynamic = methodMeta.optionalBooleanAttributes("dynamic");
        paramList.forEach(paramMate -> {
            if (paramMate.getType() == ParamMate.TYPE_CUSTOM_ENTITY || paramMate.getType() == ParamMate.TYPE_ENTITY) {
                list.addAll(parseObjectMate(paramMate, isMulti, true));
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
                return parseParamMate(item, paramMate.getParamName(), true, methodDynamic, true);
            } else {
                return parseParamMate(item, null, false, methodDynamic, true);
            }
        }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    private ParamMapping parseParamMate(ParamMate paramMate, String parentName,
                                        boolean isMulti, boolean methodDynamic, boolean isObject) {
        String paramName = paramMate.getParamName();
        if (paramMate.getAnnotation() == null) {
            return ParamMapping.convert(paramName, underscoreName(paramName),
                    builderPlaceholder(paramName, parentName), null, methodDynamic, ConditionType.EQUAL);
        }
        // 获取参数条件
        AnnotationUtils.AnnotationMate annotationMate = AnnotationUtils.findAnnotationMate(paramMate.getAnnotation(), Condition.class);
        if (annotationMate == null) {
            throw new IllegalArgumentException("非法的注解信息");
        }
        Condition annotation = (Condition) annotationMate.getAnnotation();
        if (annotation.type() == ConditionType.IGNORE) {
            return null;
        }
        // 获取参数信息
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(paramMate.getAnnotation());
        String alias = (String) annotationAttributes.get("alias");
        boolean dynamic = (boolean) annotationAttributes.get("dynamic");
        String value = (String) annotationAttributes.get("value");

        // 处理方法参数中只有一个IN 查询的时候
        if (!isMulti && (annotation.type() == ConditionType.IN || annotation.type() == ConditionType.NOT_IN)) {
            return ParamMapping.convert("list",
                    StringUtils.hasText(value) ? value : underscoreName(paramName),
                    builderPlaceholder(paramName, parentName),
                    alias, methodDynamic || dynamic, annotation.type());
        }
        return ParamMapping.convert(paramName, StringUtils.hasText(value) ? value : underscoreName(paramName), builderPlaceholder(paramName, parentName),
                alias, dynamic || methodDynamic, annotation.type());
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
                // 处理实体对象
                // paramMeta = ParamMate.builder(methodMeta.getTableMetadata().getId().getField(), ParamMate.TYPE_ENTITY);
                paramMeta = parseCustomParameter((Class<?>) parameter.getParameterizedType(), paramName);
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
                } else if (!isEntityParam(parameterizedType.getActualTypeArguments()[0], entityClass)
                        && SqlCommandType.INSERT == methodMeta.getSqlCommand()) {
                    throw new EasyBatisException("InsertSql 的参数类型和接口类型不一致");
                } else {
                    return ParamMate.builder(paramName, ParamMate.TYPE_ENTITY);
                }
            } else {
                return ParamMate.builder(paramName, annotation);
            }

        } else {
            if (isEntityParam(parameter.getParameterizedType(), entityClass)) {
                paramMeta = ParamMate.builder(methodMeta.getTableMetadata().getId().getField(), ParamMate.TYPE_ENTITY);
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
    private void resolverAuditor(MethodMeta methodMeta, List<ParamMate> paramList) {
        if (paramList.stream().noneMatch(paramMate -> paramMate.getType() == ParamMate.TYPE_ENTITY)
                && SqlCommandType.UPDATE == methodMeta.getSqlCommand()) {
            methodMeta.getTableMetadata().getAuditorMap().values().forEach(auditorMapping -> {
                paramList.add(ParamMate.builder(auditorMapping.getField(), ParamMate.TYPE_AUDITOR,
                        chooseAuditsAnnotationType(auditorMapping.getAnnotationSet())));
            });
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
        if (methodMeta.getSqlCommand() == SqlCommandType.SELECT || methodMeta.getSqlCommand() == SqlCommandType.UPDATE) {
            paramList.add(ParamMate.builder(logic.getColumn(), ParamMate.TYPE_LOGIC));
        }
    }


//    @Deprecated
//    private ParamMapping parseParameter(Parameter parameter, String paramName, SqlCommandType command,
//                                        Class<?> entityClass, boolean dynamic) {
//        //处理接口泛型 泛型的两种情况是 主键或者是实体
//        if (parameter.getParameterizedType() instanceof TypeVariable) {
//            ParamMapping paramMeta = ParamMapping.builder(underscoreName(paramName), paramName);
//            if (isEntityParam(parameter.getParameterizedType(), entityClass)) {
//                paramMeta.setEntity(true);
//            } else if (isKeyParam(parameter.getParameterizedType())) {
//                paramMeta.setPrimaryKey(true);
//                paramMeta.setCondition(ConditionType.EQUAL);
//            } else {
//                throw new EasyBatisException("泛型类型不匹配");
//            }
//            return paramMeta;
//        } else if (parameter.getParameterizedType() instanceof ParameterizedType) { // 处理接口中的集合类型
//            ParameterizedType parameterizedType = (ParameterizedType) parameter.getParameterizedType();
//            if (Collection.class.isAssignableFrom((Class<?>) parameterizedType.getRawType())) {
//                if (command == SqlCommandType.SELECT) {
//                    return parseAnnotation(parameter.getAnnotations(), paramName, dynamic);
//                } else if (!isEntityParam(parameterizedType.getActualTypeArguments()[0], entityClass)) {
//                    throw new EasyBatisException("InsertSql 的参数类型和接口类型不一致");
//                }
//                return ParamMapping.builder(paramName, true, true);
//            } else {
//                return ParamMapping.builder(paramName, false, false);
//            }
//        } else {
//            if (isEntityParam(parameter.getParameterizedType(), entityClass)) {
//                return ParamMapping.builder(paramName, true, false);
//            } else if (Reflection.isCustomObject(parameter.getType()) && command == SqlCommandType.SELECT) {
//                return parseCustomParameter((Class<?>) parameter.getParameterizedType(), paramName);
//            } else {
//                return parseAnnotation(parameter.getAnnotations(), paramName, dynamic);
//            }
//        }
//    }


//    private ParamMapping parseCustomParameter(Class<?> type, String paramName) {
//        ParamMapping param = ParamMapping.builder(this.underscoreName(paramName), paramName, true);
//        List<Field> fieldList = Reflection.getField(type);
//        List<ParamMapping> paramList = new ArrayList<>();
//        for (Field field : fieldList) {
//            ParamMapping paramMetaData = parseAnnotation(field.getDeclaredAnnotations(),
//                    field.getName(), true);
//            paramMetaData.setParentParamName(param.getParamName());
//            paramList.add(paramMetaData);
//            param.setDynamicType(DynamicType.DYNAMIC);
//        }
//        param.setChildList(paramList);
//        return param;
//    }

//
//    /**
//     * @param annotations 查询条件上的注解信息
//     * @param paramName   参数名
//     * @return ParamMetaData
//     */
//    public ParamMapping parseAnnotation(Annotation[] annotations, String paramName, boolean methodGlobalDynamic) {
//        Annotation ignore = chooseAnnotationType(annotations, Ignore.class);
//        ParamMapping param = ParamMapping.builder(underscoreName(paramName), paramName, methodGlobalDynamic);
//        if (ignore != null) {
//            param.setCondition(ConditionType.IGNORE);
//            return param;
//        }
//        Annotation queryAnnotation = chooseQueryAnnotationType(annotations);
//        if (queryAnnotation != null) {
//            Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(queryAnnotation);
//            param.mergeConditionAnnotation(map);
//            Condition condition = AnnotationUtils.findAnnotation(queryAnnotation.annotationType(), Condition.class);
//            if (condition == null) {
//                throw new EasyBatisException("注解格式不符合规范");
//            } else {
//                param.setCondition(condition.type());
//            }
//        }
//        Annotation setParamAnnotation = chooseSetParamAnnotationType(annotations);
//        if (queryAnnotation != null && setParamAnnotation != null) {
//            throw new EasyBatisException("查询条件和设置参数注解无法在一起使用");
//        } else if (setParamAnnotation != null) {
//            param.setCondition(ConditionType.SET_PARAM);
//        } else if (queryAnnotation == null) {
//            param.setCondition(ConditionType.EQUAL);
//        } else {
//            return param;
//        }
//        return param;
//    }

//    /**
//     * @param annotations 查询条件上的注解信息
//     * @param paramName   参数名
//     * @return ParamMetaData
//     */
//    public Condition parseCondtion(Annotation[] annotations, String paramName, boolean methodGlobalDynamic) {
//        Annotation ignore = chooseAnnotationType(annotations, Condition.class);
//        if (ignore != null) {
//            return ConditionType.IGNORE
//        }
//        Annotation queryAnnotation = chooseQueryAnnotationType(annotations);
//        if (queryAnnotation != null) {
//            Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(queryAnnotation);
//            param.mergeConditionAnnotation(map);
//            Condition condition = AnnotationUtils.findAnnotation(queryAnnotation.annotationType(), Condition.class);
//            if (condition == null) {
//                throw new EasyBatisException("注解格式不符合规范");
//            } else {
//                param.setCondition(condition.type());
//            }
//        }
//        Annotation setParamAnnotation = chooseSetParamAnnotationType(annotations);
//        if (queryAnnotation != null && setParamAnnotation != null) {
//            throw new EasyBatisException("查询条件和设置参数注解无法在一起使用");
//        } else if (setParamAnnotation != null) {
//            param.setCondition(ConditionType.SET_PARAM);
//        } else if (queryAnnotation == null) {
//            param.setCondition(ConditionType.EQUAL);
//        } else {
//            return param;
//        }
//        return param;
//    }


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
     * 检查是否是审计字段 如果是则返回对应注解
     */
    public Annotation chooseAuditsAnnotationType(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (AuditorAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    public Annotation chooseAuditsAnnotationType(Set<Annotation> annotationSet) {
        for (Annotation annotation : annotationSet) {
            if (AuditorAnnoSet.contains(annotation.annotationType())) {
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


//    /**
//     * 请见方法 parseMethodParam1
//     *
//     * @param methodMeta
//     * @return
//     */
//    @Deprecated
//    private List<ParamMapping> parseMethodParam(MethodMeta methodMeta) {
//        Parameter[] parameters = methodMeta.getMethod().getParameters();
//        List<String> paramNames = ParamNameUtil.getParamNames(methodMeta.getMethod());
//        List<ParamMapping> list = new ArrayList<>();
//        for (int i = 0; i < paramNames.size(); i++) {
//            ParamMapping paramMeta = parseParameter(parameters[i], paramNames.get(i), methodMeta.getSqlCommand()
//                    , methodMeta.getTableMetadata().getSource(), methodMeta.isDynamic());
//            list.add(paramMeta);
//        }
//        LogicMapping logic = methodMeta.getTableMetadata().getLogic();
//        // 增删改查都都需要增删改查
//        if (logic != null && list.stream().noneMatch(ParamMapping::isEntity)) {
//            ParamMapping logicParamMeta = ParamMapping.builder(logic.getColumn(), logic.getField(), ConditionType.EQUAL,
//                    null, false, false);
//            logicParamMeta.setSimulate(true);
//            list.add(logicParamMeta);
//        }
//        if (list.stream().anyMatch(ParamMapping::isSetParam) && SqlCommandType.UPDATE == methodMeta.getSqlCommand()) {
//            methodMeta.getTableMetadata().getAuditorMap().values().stream()
//                    .filter(item -> item.getType().command() == SqlCommandType.UPDATE)
//                    .sorted(Comparator.comparing(s1 -> s1.getType().ordinal()))
//                    .forEach(item -> {
//                        ParamMapping setParam = ParamMapping
//                                .builder(item.getColumn(), item.getField(), ConditionType.SET_PARAM,
//                                        null, false, false);
//                        setParam.setSimulate(true);
//                        list.add(setParam);
//                    });
//        }
//        return list;
//    }


}
