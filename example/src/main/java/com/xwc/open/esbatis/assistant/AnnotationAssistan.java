package com.xwc.open.esbatis.assistant;


import com.xwc.open.esbatis.anno.auditor.*;
import com.xwc.open.esbatis.anno.condition.filter.Condition;
import com.xwc.open.esbatis.anno.condition.filter.Equal;
import com.xwc.open.esbatis.anno.table.*;
import com.xwc.open.esbatis.enums.ConditionType;
import com.xwc.open.esbatis.interfaces.Page;
import com.xwc.open.esbatis.interfaces.SyntaxTemplate;
import com.xwc.open.esbatis.meta.*;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.reflection.ParamNameUtil;
import org.apache.ibatis.session.Configuration;
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

    private Configuration configuration;
    private SyntaxTemplate template;
    private int DEFAULT_ORDER = 99;

    public AnnotationAssistan(Configuration configuration, SyntaxTemplate template) {
        this.configuration = configuration;
        this.template = template;
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
        Table tableAnno = AnnotationUtils.findAnnotation(entityType, Table.class);
        if (tableAnno == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
        Object name = AnnotationUtils.getValue(tableAnno, "name");
        if (name == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
        EntityMate entity = new EntityMate(name.toString());
        List<Field> fieldArr = entityField(entityType);
        for (Field field : fieldArr) {
            if (isIgnore(field)) continue;
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
            Loglic loglic = AnnotationUtils.findAnnotation(field, Loglic.class);
            if (loglic != null) {
                LoglicAttribute loglicAttribute = new LoglicAttribute(attribute, loglic.valid(), loglic.invalid());
                updateColum(loglic, loglicAttribute);
                entity.setLogic(loglicAttribute);
                continue;
            }
            Annotation[] annotations = field.getDeclaredAnnotations();
            Annotation annotation = chooseAduitorAnnotationType(annotations);

            if (annotation == null) {
                entity.addAttribute(attribute);
            } else {
                annotation = AnnotationUtils.findAnnotation(field, annotation.annotationType()); //使Spring 的 AliasFor 注解生效
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


    /**
     * 解析查询实体
     *
     * @param method
     * @return
     */
    public ConditionMate parseQuery(Method method) {
        ConditionMate conditionMate = new ConditionMate(template);
        Class<?>[] parameterTypes = method.getParameterTypes();
        int paramCount = parameterTypes.length;
        if (paramCount != 1) throw new BindingException("parameters filed null Unable to build Sql");
        Class<?> clazz = parameterTypes[0];
        List<Field> fields = Reflection.getField(clazz);
        ConditionAttribute conditionAttribute;
        for (Field f : fields) {
            if (isIgnore(f)) continue;
            Attribute attribute = analysisAttribute(f, clazz);
            Annotation annotation = chooseAnnotationType(f);
            if (annotation == null) {
                conditionAttribute = new ConditionAttribute(attribute, DEFAULT_ORDER, ConditionType.EQUEL);
                conditionMate.add(conditionAttribute);
            } else {
                annotation = AnnotationUtils.findAnnotation(f, annotation.annotationType());
                updateColum(annotation, attribute);
                Object index = AnnotationUtils.getValue(annotation, "index");
                Condition condition = AnnotationUtils.getAnnotation(annotation, Condition.class);
                conditionAttribute = new ConditionAttribute(attribute, (int) index, condition.type());
                conditionMate.add(conditionAttribute);
            }

        }
        if (Page.class.isAssignableFrom(clazz)) {
            conditionMate.add(new ConditionAttribute(
                    new Attribute("limitStart", "limit_start", null, null)
                    , 99, ConditionType.LIMIT_START));
            conditionMate.add(new ConditionAttribute(
                    new Attribute("limitOffset", "limit_offset", null, null)
                    , 99, ConditionType.LIMIT_OFFSET));
        }

        return conditionMate;
    }

    /**
     * 解析查询方法
     *
     * @param method
     * @return
     */
    public QueryMate parseQueryMethod(Method method) {
        ConditionMate query = new ConditionMate(template);
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int paramCount = parameterAnnotations.length;
        List<String> paramNames = ParamNameUtil.getParamNames(method);
        for (int i = 0; i < paramCount; ++i) {
            ConditionAttribute paramFilter = analysisParam(parameterAnnotations[i], i, paramNames.get(i));
            query.addFilter(paramFilter);
        }
        return query;
    }

    /**
     * 获取对象属性的过滤条件
     *
     */
    public ConditionAttribute analysisParam(Annotation[] annotations, Integer index, String paramNames) {
        Annotation annotation = chooseAnnotationType(annotations);
        Attribute attribute = new Attribute(paramNames, underscoreName(paramNames), null, null);
        if (annotation == null) {
            return new ConditionAttribute(attribute, index + DEFAULT_ORDER, ConditionType.EQUEL);
        } else {
            annotation = AnnotationUtils.findAnnotation(annotation.annotationType());
            updateColum(annotation, attribute);
            int annoIndex = (int) AnnotationUtils.getValue(annotation, "index");
            Condition condition = AnnotationUtils.getAnnotation(annotation, Condition.class);
            return new ConditionAttribute(attribute, annoIndex, condition.type());
        }
    }

    /**
     * 获取方法属性的注解属性
     *
     * @param annotations
     * @param paramName
     * @return
     */
    public FilterColumMate queryMate(Annotation[] annotations, int index, String paramName) {
        Annotation annotation = chooseAnnotationType(annotations);
        FilterColumMate filter = new FilterColumMate();
        if (annotation == null) {
            filter.setConditionEnum(ConditionEnum.EQUEL);
            filter.setIndex(DEFAULT_ORDER + index);
            filter.setField(paramName);
            filter.setColunm(underscoreName(paramName));
        } else {
            try {
                String colum = (String) annotation.getClass().getMethod("colum").invoke(annotation);
                filter.setField(paramName);
                filter.setColunm(colum.isEmpty() ? underscoreName(paramName) : underscoreName(colum));
                int annoIndex = (int) annotation.getClass().getMethod("index").invoke(annotation);
                filter.setIndex(annoIndex == DEFAULT_ORDER ? annoIndex + DEFAULT_ORDER : annoIndex);
                ConditionEnum type = (ConditionEnum) annotation.getClass().getMethod("type").invoke(annotation);
                filter.setConditionEnum(type);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return filter;
    }

    /**
     * 判断是否是忽略字段
     */
    public boolean isIgnore(Field field) {
        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
    }

    public Annotation chooseAnnotationType(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            if (queryAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    public Annotation chooseAnnotationType( Annotation[] annotations) {
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
}
