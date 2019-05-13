package com.xwc.esbatis.assistant;

import com.xwc.esbatis.anno.condition.enhance.*;
import com.xwc.esbatis.anno.enums.ConditionEnum;
import com.xwc.esbatis.anno.table.Colum;
import com.xwc.esbatis.anno.table.Ignore;
import com.xwc.esbatis.anno.table.PrimaryKey;
import com.xwc.esbatis.anno.table.Table;
import com.xwc.esbatis.meta.ColumMate;
import com.xwc.esbatis.meta.EntityMate;
import com.xwc.esbatis.meta.FilterColumMate;
import com.xwc.esbatis.meta.QueryMate;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.reflection.ParamNameUtil;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    private static int DEFAULT_ORDER = 99;
    private Configuration configuration;

    public AnnotationAssistan(Configuration configuration) {
        this.configuration = configuration;
    }

    private static Set<Class<? extends Annotation>> queryAnnoSet = new HashSet<>();

    static {
        queryAnnoSet.add(Equal.class);
        queryAnnoSet.add(In.class);
        queryAnnoSet.add(IsNull.class);
        queryAnnoSet.add(Like.class);
        queryAnnoSet.add(LimitOffset.class);
        queryAnnoSet.add(LimitStart.class);
        queryAnnoSet.add(NotEqual.class);
        queryAnnoSet.add(NotIn.class);
        queryAnnoSet.add(NotLeftLike.class);
        queryAnnoSet.add(NotLike.class);
        queryAnnoSet.add(NotNull.class);
        queryAnnoSet.add(NotRightLike.class);
        queryAnnoSet.add(RightLike.class);
        queryAnnoSet.add(LeftLike.class);
        queryAnnoSet.add(com.xwc.esbatis.anno.condition.enhance.Set.class);
    }

    /**
     * 解析实体信息
     */
    public EntityMate parseEntityMate(Class<?> entityType) {
        Table tableAnno = entityType.getDeclaredAnnotation(Table.class);
        if (tableAnno == null) throw new RuntimeException(entityType.getName() + "not find @Table Annotaion");
        EntityMate table = new EntityMate();
        table.setTableName(tableAnno.value());
        ColumMate columMate;
        Field[] fieldArr = entityType.getDeclaredFields();
        for (Field field : fieldArr) {
            columMate = analysisColum(field, entityType);
            if (columMate == null || columMate.isIgnore()) {
                continue;
            } else if (columMate.getKeyEnum() != null) {
                table.addPrimaryKey(columMate);
                table.setKeyEnum(columMate.getKeyEnum());
            } else {
                table.addDefault(columMate);
            }

        }
        return table.validate();
    }


    private ColumMate analysisColum(Field field, Class<?> clazz) {
        ColumMate mapper = new ColumMate(field.getName(), underscoreName(field.getName()));
        try {
            Reflection.setter(field, clazz);
            Reflection.getter(field, clazz);
        } catch (NoSuchMethodException e) {
            return null;
        }
        Ignore ignore = AnnotationUtils.findAnnotation(field, Ignore.class);
        PrimaryKey primaryKey = AnnotationUtils.findAnnotation(field, PrimaryKey.class);
        if (ignore != null) mapper.setIgnore(true);
        if (primaryKey != null) mapper.setKeyEnum(primaryKey.type());
        Colum colum = AnnotationUtils.findAnnotation(field, Colum.class);
        if (colum != null) mapper.setColunm(colum.colum());
        return mapper;
    }


    /**
     * 解析查询实体
     *
     * @param method
     * @return
     */
    public QueryMate parseQueryEntity(Method method) {
        QueryMate query = new QueryMate();
        int index = 0;
        Class<?>[] parameterTypes = method.getParameterTypes();
        int paramCount = parameterTypes.length;
        if (paramCount != 1) throw new BindingException("parameters filed null Unable to build Sql");
        for (Class<?> par : parameterTypes) {
            List<Field> fields = Reflection.getField(par);
            for (Field f : fields) {
                if (isIgnore(f)) continue;
                query.addFilter(queryMate(f, index));
                ++index;
            }
        }
        return query;
    }

    /**
     * 解析查询方法
     *
     * @param method
     * @return
     */
    public QueryMate parseQueryMethod(Method method) {
        QueryMate query = new QueryMate();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        int paramCount = parameterAnnotations.length;
        List<String> paramNames = ParamNameUtil.getParamNames(method);
        for (int i = 0; i < paramCount; ++i) {
            FilterColumMate paramFilter = queryMate(parameterAnnotations[i], i, paramNames.get(i));
            query.addFilter(paramFilter);
        }
        return query;
    }

    /**
     * 获取对象属性的过滤条件
     *
     * @param field
     * @return
     */
    public FilterColumMate queryMate(Field field, Integer index) {
        Annotation[] declaredAnnotations = field.getDeclaredAnnotations();
        return this.queryMate(declaredAnnotations, index, field.getName());
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
                filter.setColunm(colum.isEmpty() ? underscoreName(paramName) : colum);
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

    public Annotation chooseAnnotationType(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (queryAnnoSet.contains(annotation.annotationType())) {
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
