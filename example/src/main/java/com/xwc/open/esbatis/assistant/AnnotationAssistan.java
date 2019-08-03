package com.xwc.open.esbatis.assistant;


import com.xwc.open.esbatis.EsbatisProperties;
import com.xwc.open.esbatis.anno.auditor.*;
import com.xwc.open.esbatis.anno.condition.filter.Equal;
import com.xwc.open.esbatis.anno.table.Colum;
import com.xwc.open.esbatis.anno.table.Id;
import com.xwc.open.esbatis.anno.table.Ignore;
import com.xwc.open.esbatis.anno.table.Table;
import com.xwc.open.esbatis.meta.Attribute;
import com.xwc.open.esbatis.meta.AuditorAttribute;
import com.xwc.open.esbatis.meta.EntityMate;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/8  16:12
 * 业务：
 * 功能：用于获取注解信息
 */
public class AnnotationAssistan {
    private static EsbatisProperties properties;

    private static int DEFAULT_ORDER = 99;
    private Configuration configuration;

    public AnnotationAssistan(Configuration configuration) {
        this.configuration = configuration;
    }

    private static Set<Class<? extends Annotation>> queryAnnoSet = new HashSet<>();

    private static Set<Class<? extends Annotation>> auditorAnnoSet = new HashSet<>();

    static {
        queryAnnoSet.add(Equal.class);
    }



    static {
        auditorAnnoSet.add(CreateId.class);
        auditorAnnoSet.add(UpdateId.class);
        auditorAnnoSet.add(CreateName.class);
        auditorAnnoSet.add(UpdateName.class);
        auditorAnnoSet.add(CreateTime.class);
        auditorAnnoSet.add(UpdateTime.class);
    }

    /**
     * 解析实体信息
     */
    public EntityMate parseEntityMate(Class<?> entityType) {
        Table tableAnno = AnnotationUtils.findAnnotation(entityType,Table.class);
        if (tableAnno == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
        Object name = AnnotationUtils.getValue(tableAnno, "name");
        if(name == null)  throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
        EntityMate entity = new EntityMate(name.toString());
        List<Field> fieldArr = entityField(entityType);
        for (Field field : fieldArr) {
            Ignore ignore = AnnotationUtils.findAnnotation(field, Ignore.class);
            if (ignore != null) continue;
            Attribute attribute = analysisAttribute(field, entityType);
            if (attribute == null) continue;
            Id id = AnnotationUtils.findAnnotation(field, Id.class);
            if (id != null) {
                Object value = AnnotationUtils.getValue(id);
                if (value == null) continue;
                attribute.updateColunm(underscoreName(value.toString()));
                entity.setId(attribute, id.type());
                continue;
            }
            Colum colum = AnnotationUtils.findAnnotation(field, Colum.class);
            if (colum != null) {
                updateColum(colum, attribute);
                entity.addAttribute(attribute);
                continue;
            }
            Annotation[] annotations = field.getDeclaredAnnotations();
            Annotation annotation = chooseAduitorAnnotationType(annotations);
            annotation = AnnotationUtils.findAnnotation(field,annotation.getClass()); //使Spring 的 AliasFor 注解生效
            if (annotation == null) {
                entity.addAttribute(attribute);
            } else {

                updateColum(annotation, attribute);
                Boolean hidden;
                Object object = AnnotationUtils.getValue(annotation, "hidden");
                if (!(object instanceof Boolean) || object == null) {
                    hidden = false;
                } else {
                    hidden = (Boolean) object;
                }
                Auditor auditor = AnnotationUtils.findAnnotation(annotation.getClass(), Auditor.class);
                AuditorAttribute auditorAttribute = new AuditorAttribute(attribute, auditor.type(), hidden);
                entity.addAuditorAttribute(auditorAttribute);
                continue;
            }
        }
        return entity.validate(entityType);
    }

    private void updateColum(Annotation annotation, Attribute attribute) {
        Object value = AnnotationUtils.getValue(annotation);
        if (value == null) return;
        attribute.updateColunm(underscoreName(value.toString()));
    }

    private List<Field> entityField(Class<?> entityType) {
        Class<?> superclass = entityType.getSuperclass();
        ArrayList<Field> list = new ArrayList<>();
        Field[] fields = entityType.getDeclaredFields();
        for (Field field : fields) {
            list.add(field);
        }
        if (superclass.equals(Object.class)) {
            return list;
        }
        list.addAll(entityField(superclass));
        return list;
    }


    private Attribute analysisAttribute(Field field, Class<?> clazz) {
        Method setter;
        Method getter;
        try {
            setter = Reflection.setter(field, clazz);
            getter = Reflection.getter(field, clazz);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return new Attribute(field.getName(), underscoreName(field.getName()), getter, setter);
    }


//    /**
//     * 解析查询实体
//     *
//     * @param method
//     * @return
//     */
//    public QueryMate parseQueryEntity(Method method) {
//        QueryMate query = new QueryMate();
//        int index = 0;
//        Class<?>[] parameterTypes = method.getParameterTypes();
//        int paramCount = parameterTypes.length;
//        if (paramCount != 1) throw new BindingException("parameters filed null Unable to build Sql");
//        for (Class<?> par : parameterTypes) {
//            List<Field> fields = Reflection.getField(par);
//            for (Field f : fields) {
//                if (isIgnore(f)) continue;
//                query.addFilter(queryMate(f, index));
//                ++index;
//            }
//            if (Page.class.isAssignableFrom(par)) {
//                query.setStart(new FilterColumMate("limitStart", "limit_start", ConditionEnum.LIMIT_START, 99));
//                query.setOffset(new FilterColumMate("limitOffset", "limit_offset", ConditionEnum.LIMIT_OFFSET, 99));
//            }
//        }
//        return query;
//    }

//    /**
//     * 解析查询方法
//     *
//     * @param method
//     * @return
//     */
//    public QueryMate parseQueryMethod(Method method) {
//
//        QueryMate query = new QueryMate();
//        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
//        int paramCount = parameterAnnotations.length;
//        List<String> paramNames = ParamNameUtil.getParamNames(method);
//        for (int i = 0; i < paramCount; ++i) {
//            FilterColumMate paramFilter = queryMate(parameterAnnotations[i], i, paramNames.get(i));
//            query.addFilter(paramFilter);
//        }
//        return query;
//    }

//    /**
//     * 获取对象属性的过滤条件
//     *
//     * @param field
//     * @return
//     */
//    public FilterColumMate queryMate(Field field, Integer index) {
//        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
//        return this.queryMate(declaredAnnotations, index, field.getName());
//    }

//    /**
//     * 获取方法属性的注解属性
//     *
//     * @param annotations
//     * @param paramName
//     * @return
//     */
//    public FilterColumMate queryMate(Annotation[] annotations, int index, String paramName) {
//        Annotation annotation = chooseAnnotationType(annotations);
//        FilterColumMate filter = new FilterColumMate();
//        if (annotation == null) {
//            filter.setConditionEnum(ConditionEnum.EQUEL);
//            filter.setIndex(DEFAULT_ORDER + index);
//            filter.setField(paramName);
//            filter.setColunm(underscoreName(paramName));
//        } else {
//            try {
//                String colum = (String) annotation.getClass().getMethod("colum").invoke(annotation);
//                filter.setField(paramName);
//                filter.setColunm(colum.isEmpty() ? underscoreName(paramName) : underscoreName(colum));
//                int annoIndex = (int) annotation.getClass().getMethod("index").invoke(annotation);
//                filter.setIndex(annoIndex == DEFAULT_ORDER ? annoIndex + DEFAULT_ORDER : annoIndex);
//                ConditionEnum type = (ConditionEnum) annotation.getClass().getMethod("type").invoke(annotation);
//                filter.setConditionEnum(type);
//            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
//                e.printStackTrace();
//            }
//        }
//        return filter;
//    }

    /**
     * 判断是否是忽略字段
     */
    public boolean isIgnore(Field field) {
        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
    }

    public Annotation chooseAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (queryAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    public Annotation chooseAduitorAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (auditorAnnoSet.contains(annotation.annotationType())) {
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

    public static void setProperties(EsbatisProperties properties) {
        AnnotationAssistan.properties = properties;
    }
}
