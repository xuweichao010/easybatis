package com.xwc.open.esbatis.assistant;


import com.xwc.open.esbatis.anno.auditor.*;
import com.xwc.open.esbatis.anno.condition.filter.Condition;
import com.xwc.open.esbatis.anno.condition.filter.Equal;
import com.xwc.open.esbatis.anno.condition.filter.SetParam;
import com.xwc.open.esbatis.anno.table.*;
import com.xwc.open.esbatis.enums.ConditionType;
import com.xwc.open.esbatis.interfaces.Page;
import com.xwc.open.esbatis.interfaces.SyntaxTemplate;
import com.xwc.open.esbatis.meta.*;
import org.apache.ibatis.reflection.ParamNameUtil;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 创建人：徐卫超
 * 创建时间：2019/5/8  16:12
 * 业务：
 * 功能：用于获取注解信息
 */
@SuppressWarnings("all")
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


    /**
     * 获取对象中的属性
     */
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

    /**
     * 解析属性
     */
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
     * 解析方法上的查询条件
     */
    public ConditionMate parseSelect(Method method) {
        if (isCustomObject(method)) {
            return parseSelectObject(method);
        } else {
            return parseSelectParam(method);
        }
    }

    public boolean isCustomObject(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 1) {
            if (!isDefualtClass(parameters[0].getClass())) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean isParamSet(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 1) {
            SetParam annotation = AnnotationUtils.findAnnotation(parameters[0].getType(), SetParam.class);
            if (annotation == null) return false;
        }
        return true;
    }

    public boolean paramentersIsCollections(Method method) {
        Parameter[] parameters = method.getParameters();
        if (parameters.length == 1) {
            if (Collection.class.isAssignableFrom(parameters[0].getType())) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new RuntimeException("错误");
        }
    }

    /**
     * 解析查询实体
     */
    private ConditionMate parseSelectObject(Method method) {
        ConditionMate conditionMate = new ConditionMate(template);
        Class<?> clazz = method.getParameterTypes()[0];
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
     * 解析查询参数
     */
    private ConditionMate parseSelectParam(Method method) {
        ConditionMate condition = new ConditionMate(template);
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int paramCount = parameterAnnotations.length;
        List<String> paramNames = ParamNameUtil.getParamNames(method);
        for (int i = 0; i < paramCount; ++i) {
            ConditionAttribute paramFilter = analysisParam(parameterAnnotations[i], i, paramNames.get(i));
            condition.add(paramFilter);
        }
        return condition;
    }

    /**
     * 获取注解中的值
     */
    public static Object getValue(Annotation annotation, @Nullable String attributeName) {
        if (annotation == null || !StringUtils.hasText(attributeName)) {
            return null;
        }
        try {
            Method method = annotation.annotationType().getDeclaredMethod(attributeName);
            AliasFor aliasFor = AnnotationUtils.findAnnotation(method, AliasFor.class);
            if (aliasFor == null) {
                ReflectionUtils.makeAccessible(method);
                return method.invoke(annotation);
            } else {
                ReflectionUtils.makeAccessible(method);
                Object value = method.invoke(annotation);
                if (value.equals(AnnotationUtils.getDefaultValue(annotation, attributeName))) {
                    return getValue(annotation, aliasFor.value());
                } else {
                    return value;
                }
            }
        } catch (Throwable ex) {
            return null;
        }
    }

    /**
     * 获取对象属性的过滤条件
     */
    private ConditionAttribute analysisParam(Annotation[] annotations, Integer index, String paramNames) {
        Annotation annotation = chooseAnnotationType(annotations);
        Attribute attribute = new Attribute(paramNames, underscoreName(paramNames), null, null);
        if (annotation == null) {
            return new ConditionAttribute(attribute, index + DEFAULT_ORDER, ConditionType.EQUEL);
        } else {
            updateCustomColum(annotation, attribute);
            int annoIndex = (int) AnnotationUtils.getValue(annotation, "index");
            Condition condition = AnnotationUtils.getAnnotation(annotation, Condition.class);
            return new ConditionAttribute(attribute, annoIndex, condition.type());
        }
    }

    /**
     * 判断是否是忽略字段
     */
    private boolean isIgnore(Field field) {
        return AnnotationUtils.findAnnotation(field, Ignore.class) != null;
    }

    /**
     * 判断属性是否有条件查询注解
     */
    private Annotation chooseAnnotationType(Field field) {
        Annotation[] annotations = field.getDeclaredAnnotations();
        return this.chooseAduitorAnnotationType(annotations);
    }

    /**
     * 判断注解数组中是否有查询条件注解
     */
    private Annotation chooseAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (queryAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 检查
     */
    public Annotation chooseAduitorAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (auditorAnnoSet.contains(annotation.annotationType())) {
                return annotation;
            }
        }
        return null;
    }

    /**
     * 判断对象是否是默认对象
     */
    public static boolean isDefualtClass(Class<?> clazz) {
        return clazz != null && clazz.getClassLoader() == null;
    }


    /**
     * 更新列属性
     */
    private void updateColum(Annotation annotation, Attribute attribute) {
        Object value = AnnotationUtils.getValue(annotation);
        if (value == null) return;
        attribute.updateColunm(underscoreName(value.toString()));
    }

    /**
     * 更新列属性
     */
    private void updateCustomColum(Annotation annotation, Attribute attribute) {
        Object value = this.getValue(annotation, "value");
        if (value == null || value.toString().isEmpty()) return;
        attribute.updateColunm(underscoreName(value.toString()));
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
