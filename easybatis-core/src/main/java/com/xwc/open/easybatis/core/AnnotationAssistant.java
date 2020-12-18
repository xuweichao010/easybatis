package com.xwc.open.easybatis.core;


import com.xwc.open.easybatis.core.EasybatisConfiguration;
import com.xwc.open.easybatis.core.anno.DeleteSql;
import com.xwc.open.easybatis.core.anno.InsertSql;
import com.xwc.open.easybatis.core.anno.SelectSql;
import com.xwc.open.easybatis.core.anno.UpdateSql;
import com.xwc.open.easybatis.core.anno.auditor.*;
import com.xwc.open.easybatis.core.anno.condition.Count;
import com.xwc.open.easybatis.core.anno.condition.Distinct;
import com.xwc.open.easybatis.core.anno.condition.Join;
import com.xwc.open.easybatis.core.anno.condition.PrimaryKey;
import com.xwc.open.easybatis.core.anno.condition.filter.*;
import com.xwc.open.easybatis.core.anno.table.Column;
import com.xwc.open.easybatis.core.anno.table.Ignore;
import com.xwc.open.easybatis.core.anno.table.Table;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.commons.Reflection;
import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.enums.IdType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.core.support.table.AuditorColumn;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;
import com.xwc.open.easybatis.core.support.table.PrimayKey;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.reflection.ParamNameUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
        if (tableAnno == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
        String name = (String) AnnotationUtils.getValue(tableAnno, "value");
        if (StringUtils.isEmpty(name) && configuration.getTablePrefix() != null) {
            return configuration.getTablePrefix() + underscoreName(entityType.getSimpleName());
        }
        //TODO 后期支持达式
        if (name == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
        return String.valueOf(name);
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
            //处理 普通属性
            Column annotation = AnnotationUtils.findAnnotation(field, Column.class);
            ColumnMeta columnMeta = analysisColunm(field, annotation, entityType);
            if (columnMeta != null) {
                table.addColumn(columnMeta);
            }

//            Annotation aduitorAnnotationType = chooseAduitorAnnotationType(field);
//            if (aduitorAnnotationType != null) {
//                AuditorColumn auditor = auditor(aduitorAnnotationType, field, entityType);
//                if (auditor != null) table.addAuditor(auditor);
//            } else {
//                //处理主键
//                PrimayKey primayKey = primayKey(field, entityType);
//                if (primayKey != null && table.getId() != null) {
//                    throw new EasyBatisException(entityType.getName() + "发现多个主键声明");
//                } else if (primayKey != null) {
//                    table.setId(primayKey);
//                    continue;
//                }
//                //处理逻辑删除
//                LoglicColumn loglic = loglic(field, entityType);
//                if (loglic != null && table.getLoglic() != null) {
//                    throw new EasyBatisException(entityType.getName() + "发现多个逻辑字段声明");
//                } else if (loglic != null) {
//                    table.setLoglic(loglic);
//                    continue;
//                }
//
//            }
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
            return parseSelectMethodMate(method, tableMetadata);
        } else if (operationAnnotationType instanceof InsertSql) {
            // TODO 插入情况
        } else if (operationAnnotationType instanceof UpdateSql) {

        } else if (operationAnnotationType instanceof DeleteSql) {

        }

        return null;
    }

    public MethodMeta parseSelectMethodMate(Method method, TableMeta tableMetadata) {
        MethodMeta metadata = MethodMeta.builder()
                .count(AnnotationUtils.findAnnotation(method, Count.class))
                .distinct(AnnotationUtils.findAnnotation(method, Distinct.class))
                .join(AnnotationUtils.findAnnotation(method, Join.class))
                .key(AnnotationUtils.findAnnotation(method, PrimaryKey.class))
                .paramMetaList(parseMethodParam(method))
                .tableMetadata(tableMetadata)
                .methodName(method.getName())
                .build();
        return metadata;
    }

    private List<ParamMeta> parseMethodParam(Method method) {
        ArrayList<ParamMeta> paramList = new ArrayList<>();
        ArrayList<ParamMeta> ObjectList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        List<String> paramNames = ParamNameUtil.getParamNames(method);
        for (int i = 0; i < paramNames.size(); i++) {
            boolean customObject = Reflection.isCustomObject(parameters[i].getType());
            //基础类型且参数只有一个
            if (parameters.length == 1 && !customObject) {
                paramList.add(parseParameter(parameters[i], paramNames.get(i), i));
            } else if (parameters.length == 1) { // 参数有多个且是自定义类型
                ObjectList.addAll(parseCustomParameter(parameters[i], paramNames.get(i), i, false));
            } else { // 参数可能是基础加混合类型
                if (customObject) {
                    ObjectList.addAll(parseCustomParameter(parameters[i], paramNames.get(i), i, true));
                } else {
                    paramList.add(parseParameter(parameters[i], paramNames.get(i), i));
                }
            }
        }
        paramList.addAll(ObjectList);
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
    public List<ParamMeta> parseCustomParameter(Parameter parameter, String paramName, int index, boolean isMulti) {
        List<Field> fieldList = Reflection.getField(parameter.getType());
        List<ParamMeta> paramList = new ArrayList<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            ParamMeta paramMetaData = createQuery(field.getAnnotations(),
                    field.getName(), index, true, isMulti ? paramName : null);
            paramList.add(paramMetaData);
        }
        return paramList;
    }

    public ParamMeta parseParameter(Parameter parameter, String paramName, int index) {
        return createQuery(parameter.getAnnotations(), paramName, index, Reflection.isCustomObject(parameter.getType()), null);
    }

    /**
     * @param annotations 查询条件上的注解信息
     * @param paramName   参数名
     * @param index       参数所在位置
     * @param isCustom    是否是自定义对象
     * @param prefix      参数名 是有需要携带前缀
     * @return ParamMetaData
     */
    public ParamMeta createQuery(Annotation[] annotations, String paramName, int index, boolean isCustom, String prefix) {
        ParamMeta param = new ParamMeta();
        param.setParamName(StringUtils.hasText(prefix) ? prefix + "." + paramName : paramName);
        Annotation annotation = chooseQueryAnnotationType(annotations);
        if (annotation == null) {
            param.setCondition(ConditionType.EQUEL);
            param.setColumnName(underscoreName(paramName));
        } else {
            Map<String, Object> map = AnnotationUtils.getAnnotationAttributes(annotation);
            param.setColumnName((String) map.get("value"));
            param.setAlias((String) map.get("alias"));
            Condition condition = AnnotationUtils.findAnnotation(annotation.annotationType(), Condition.class);
            if (condition == null) {
                throw new EasyBatisException("注解格式不符合规范");
            }
            param.setCondition(condition.type());
        }
        param.setCustom(isCustom);
        return param;
    }

    private int getQueryIndex(int annoIndex, int paramIndex) {
        return annoIndex == -1 ? paramIndex : annoIndex;
    }


    /**
     * 获取主键信息
     *
     * @param field 字段信息
     * @param clazz 实体的Class
     * @return 当主键信息存在时 返回 PrimayKey 否则返回空
     */
    public PrimayKey primayKey(Field field, Class<?> clazz) {
        com.xwc.open.easybatis.core.anno.table.Id id = AnnotationUtils.findAnnotation(field, com.xwc.open.easybatis.core.anno.table.Id.class);
        if (id == null) return null;
        ColumnMeta columnMeta = analysisColunm(field, id, clazz);
        if (columnMeta == null) return null;
        return new PrimayKey(columnMeta, id.type() == IdType.GLOBAL ? configuration.useGlobalPrimaKeyType() : id.type());
    }

    /**
     * 获取逻辑字段信息
     *
     * @param field 字段信息
     * @param clazz 实体的Class
     * @return 当逻辑属性存在时返回 LoglicColumn 否则返回空
     */
    public LoglicColumn loglic(Field field, Class<?> clazz) {
        com.xwc.open.easybatis.core.anno.table.Loglic loglic = AnnotationUtils.findAnnotation(field, com.xwc.open.easybatis.core.anno.table.Loglic.class);
        if (loglic == null) return null;
        ColumnMeta columnMeta = analysisColunm(field, loglic, clazz);
        if (columnMeta == null) return null;
        return new LoglicColumn(columnMeta, loglic.invalid(), loglic.invalid());
    }

    /**
     * 获取审计注解的信息
     *
     * @param annotation 审计注解
     * @param field      字段信息
     * @param clazz      实体的Class
     * @return 当审计信息存在时返回AuditorColumn 否则返回空
     */
    public AuditorColumn auditor(Annotation annotation, Field field, Class<?> clazz) {
        Auditor auditor = AnnotationUtils.findAnnotation(annotation.getClass(), Auditor.class);
        if (auditor == null) return null;
        ColumnMeta columnMeta = analysisColunm(field, annotation, clazz);
        if (columnMeta == null) return null;
        return new AuditorColumn(columnMeta, auditor.type());
    }

    /**
     * 解析字段的注解信息
     *
     * @param field      字段
     * @param annotation 字段描述注解信息
     * @param clazz      实体的class对象
     * @return 字段和数据表的字段映射关系 如果存在关系则返回Column 负责返回空
     */
    private ColumnMeta analysisColunm(Field field, Annotation annotation, Class<?> clazz) {
        Method getter;
        Method setter;
        try {
            getter = Reflection.getter(field, clazz);
            setter = Reflection.setter(field, clazz);
        } catch (NoSuchMethodException e) {
            return null;
        }
        Map<String, Object> map;
        if (annotation != null) {
            map = AnnotationUtils.getAnnotationAttributes(annotation);
        } else {
            map = new HashMap<>();
        }
        ColumnMeta columnMeta = new ColumnMeta(map, getter, setter, field.getName());
        if (!StringUtils.hasText(columnMeta.getColumn())) {
            columnMeta.setColumn(underscoreName(columnMeta.getField()));
        } else {
            columnMeta.setResult(true);
        }
        return columnMeta;
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
