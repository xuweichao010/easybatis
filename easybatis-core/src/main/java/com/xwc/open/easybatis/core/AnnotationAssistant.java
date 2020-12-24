package com.xwc.open.easybatis.core;


import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.auditor.*;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.anno.table.*;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;
import com.xwc.open.easybatis.core.support.table.IdMeta;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.ParamNameUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/8  16:12
 * 业务：
 * 功能：用于获取注解信息
 */
public class AnnotationAssistant {

    private final EasybatisConfiguration configuration;

    public AnnotationAssistant(EasybatisConfiguration configuration) {
        this.configuration = configuration;
    }

    private final static Set<Class<? extends Annotation>> auditorAnnoSet = Stream.of(CreateId.class,
            UpdateId.class, CreateName.class, UpdateName.class, CreateTime.class, UpdateTime.class).collect(Collectors.toSet());
    private final static Set<Class<? extends Annotation>> operationAnnoSet = Stream
            .of(SelectSql.class, InsertSql.class, UpdateSql.class, DeleteSql.class).collect(Collectors.toSet());
    private static final Set<Class<? extends Annotation>> queryAnnoSet = Stream
            .of(Equal.class, NotEqual.class, IsNull.class, NotNull.class, In.class, NotIn.class,
                    Like.class, RightLike.class, LeftLike.class, GreaterThan.class, GreaterThanEqual.class, LessThan.class
                    , LessThanEqual.class, Start.class, Offset.class, OrderBy.class)
            .collect(Collectors.toSet());

    public String tableName(Class<?> entityType) {
        Table tableAnno = AnnotationUtils.findAnnotation(entityType, Table.class);
        if (tableAnno == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotation");
        String name = (String) AnnotationUtils.getValue(tableAnno, "value");
        if (StringUtils.isEmpty(name) && configuration.getTablePrefix() != null) {
            return configuration.getTablePrefix() + underscoreName(entityType.getSimpleName());
        }
        if (StringUtils.isEmpty(name)) throw new RuntimeException(entityType.getName() + " not find @Table Annotation");
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
        List<Field> fieldArr = Reflection.getField(entityType);
        for (Field field : fieldArr) {
            if (isIgnore(field)) continue;
            ColumnMeta columnMeta = analysisColunm(field, entityType);
            if (columnMeta == null) continue;
            if (columnMeta.hashAnnotationType(Id.class)) {
                Id id = columnMeta.chooseAnnotationType(Id.class);
                table.setId(new IdMeta(columnMeta, id.type() == IdType.GLOBAL ? configuration.useGlobalPrimaKeyType() : id.type(), id));
                continue;
            } else if (columnMeta.hashAnnotationType(Loglic.class)) {
                Loglic loglic = columnMeta.chooseAnnotationType(Loglic.class);
                table.setLogic(new LoglicColumn(columnMeta, loglic));
                continue;
            } else if (columnMeta.hashAnnotationType(Auditor.class)) {
//                columnMeta.chooseAnnotationType(Auditor.class);
//                table.addAuditor(new AuditorColumn(columnMeta, Auditor));
                continue;
            } else if (columnMeta.hashAnnotationType(Column.class)) {
                Column column = columnMeta.chooseAnnotationType(Column.class);
                columnMeta.mergeTableAnnotation(AnnotationUtils.getAnnotationAttributes(column));
            }
            table.addColumn(columnMeta);
        }
        return table;
    }

    /**
     * 处理方法上的注解信息
     *
     * @param method method
     * @return
     */
    public MethodMeta parseMethodMate(Method method, TableMeta tableMetadata) {
        Annotation operationAnnotationType = chooseOperationAnnotationType(method);
        if (operationAnnotationType == null) return null;
        if (operationAnnotationType instanceof SelectSql) {
            return parseSelectMethodMate(method, tableMetadata, (SelectSql) operationAnnotationType);
        } else if (operationAnnotationType instanceof InsertSql) {
            return parseInsertMethodMate(method, tableMetadata);
        } else if (operationAnnotationType instanceof UpdateSql) {

        } else if (operationAnnotationType instanceof DeleteSql) {

        }

        return null;
    }

    private MethodMeta parseInsertMethodMate(Method method, TableMeta tableMetadata) {
        MethodMeta meta = new MethodMeta();
        meta.setDynamic(false);
        meta.setTableMetadata(tableMetadata);
        meta.setMethodName(method.getName());
        meta.setSqlCommond(SqlCommandType.SELECT);
        meta.setMethod(method);
        meta.setParamMetaList(parseInsertMethodParam(meta));
        return meta;
    }

    private List<ParamMeta> parseInsertMethodParam(MethodMeta meta) {
        Type[] genericParameterTypes = meta.getMethod().getGenericParameterTypes();
        List<String> paramNames = ParamNameUtil.getParamNames(meta.getMethod());
        if (genericParameterTypes.length == 1) {
            Type genericParameterType = genericParameterTypes[0];
            if (!checkType(genericParameterTypes[0], meta.getTableMetadata().getSource())) {
                throw new EasyBatisException("InsertSql 的参数类型和接口类型不一致");
            }
            return Collections.singletonList(ParamMeta.builderInsert(paramNames.get(0), true));
        } else if (genericParameterTypes.length > 1) {
            //TODO  带处理
            return new ArrayList<>();
        } else {
            throw new EasyBatisException("InsertSql 的参数类型和接口类型不一致");
        }
    }

    private boolean checkType(Type entityType, Class<?> entityClass) {
        return entityType.getTypeName().equals("E");
    }

    public MethodMeta parseSelectMethodMate(Method method, TableMeta tableMetadata, SelectSql selectSql) {
        MethodMeta meta = new MethodMeta();
        meta.setDynamic(selectSql.dynamic());
        meta.setTableMetadata(tableMetadata);
        meta.setMethodName(method.getName());
        meta.setSqlCommond(SqlCommandType.INSERT);
        meta.setMethod(method);
        meta.setParamMetaList(parseSelectMethodParam(meta));
        return meta;
    }

    private List<ParamMeta> parseSelectMethodParam(MethodMeta methodMeta) {
        ArrayList<ParamMeta> paramList = new ArrayList<>();
        Parameter[] parameters = methodMeta.getMethod().getParameters();
        List<String> paramNames = ParamNameUtil.getParamNames(methodMeta.getMethod());
        for (int i = 0; i < paramNames.size(); i++) {
            boolean customObject = Reflection.isCustomObject(parameters[i].getType());
            //基础类型且参数只有一个
            if (parameters.length == 1 && !customObject) {
                paramList.add(parseParameter(parameters[i], paramNames.get(i), i, methodMeta.isDynamic()));
            } else if (parameters.length == 1) { // 参数有多个且是自定义类型
                paramList.addAll(parseCustomParameter(parameters[i], paramNames.get(i), i, false, methodMeta));
            } else { // 参数可能是基础加混合类型
                if (customObject) {
                    paramList.addAll(parseCustomParameter(parameters[i], paramNames.get(i), i, true, methodMeta));
                } else {
                    paramList.add(parseParameter(parameters[i], paramNames.get(i), i, methodMeta.isDynamic()));
                }
            }
        }
        return paramList;
    }

    /**
     * 解析自定义对象查询条件
     *
     * @param parameter
     * @param paramName
     * @param index
     * @return
     */
    public List<ParamMeta> parseCustomParameter(Parameter parameter, String paramName, int index, boolean isMulti, MethodMeta methodMeta) {
        List<Field> fieldList = Reflection.getField(parameter.getType());
        List<ParamMeta> paramList = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            ParamMeta paramMetaData = createQuery(field.getDeclaredAnnotations(),
                    field.getName(), index, true, isMulti ? paramName : null, true);
            paramList.add(paramMetaData);
        }
        return paramList;
    }

    public ParamMeta parseParameter(Parameter parameter, String paramName, int index, boolean methodGlobalDynamic) {
        return createQuery(parameter.getDeclaredAnnotations(), paramName, index,
                Reflection.isCustomObject(parameter.getType()), null, methodGlobalDynamic);
    }

    /**
     * @param annotations 查询条件上的注解信息
     * @param paramName   参数名
     * @param index       参数所在位置
     * @param isCustom    是否是自定义对象
     * @param prefix      参数名 是有需要携带前缀
     * @return ParamMetaData
     */
    public ParamMeta createQuery(Annotation[] annotations, String paramName, int index, boolean isCustom, String prefix, boolean methodGlobalDynamic) {
        ParamMeta param = ParamMeta.builder(underscoreName(paramName),
                StringUtils.hasText(prefix) ? prefix + "." + paramName : paramName,
                ConditionType.EQUAL, null, isCustom, methodGlobalDynamic);
        Annotation annotation = chooseQueryAnnotationType(annotations);
        if (annotation != null) {
            Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(annotation);
            param.mergeConditionAnnotation(map);
            Condition condition = AnnotationUtils.findAnnotation(annotation.annotationType(), Condition.class);
            if (condition == null) {
                throw new EasyBatisException("注解格式不符合规范");
            } else {
                param.setCondition(condition.type());
            }
        }

        return param;
    }


    private int getQueryIndex(int annoIndex, int paramIndex) {
        return annoIndex == -1 ? paramIndex : annoIndex;
    }


    /**
     * 解析字段的注解信息
     *
     * @param field 字段
     * @param clazz 实体的class对象
     * @return 字段和数据表的字段映射关系 如果存在关系则返回Column 负责返回空
     */
    private ColumnMeta analysisColunm(Field field, Class<?> clazz) {
        Method getter;
        Method setter;
        try {
            getter = Reflection.getter(field, clazz);
            setter = Reflection.setter(field, clazz);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return ColumnMeta.builder(field, getter, setter, underscoreName(field.getName()));
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
    public Annotation chooseAduitorAnnotationType(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (auditorAnnoSet.contains(annotation.annotationType())) {
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
