package com.xwc.open.easybatis.core.model;

import com.xwc.open.easybatis.core.anno.condition.filter.OrderBy;
import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.model.table.Mapping;
import lombok.Data;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Data
public class MethodMeta {


    private String methodName;
    /**
     * SQL操作的类型
     */
    private SqlCommandType sqlCommand;

    Map<String, Object> optionalAnnotationAttributes;

    private String columns;

    private String from;
    /**
     * 关联的表的实体的定义信息
     */
    TableMeta tableMetadata;

    /**
     * 方法
     */
    private Method method;
    /**
     * 参数有的定义信息
     */
    List<ParamMapping> paramMetaList;

    /**
     * 是否是多个参数
     */
    private boolean isMulti = false;


    public void addAttributes(String key, Object value) {
        this.optionalAnnotationAttributes.put(key, value);
    }

    public Object optionalAttributes(String attributes) {
        return this.optionalAnnotationAttributes.get(attributes);
    }

    public String optionalStringAttributes(String attributes) {
        Object o = this.optionalAttributes(attributes);
        if (o == null) {
            return null;
        }
        return (String) o;
    }

    public Integer optionalIntAttributes(String attributes) {
        Object o = this.optionalAttributes(attributes);
        if (o == null) {
            return null;
        }
        return (int) o;
    }

    public Boolean optionalBooleanAttributes(String attributes) {
        Object o = this.optionalAttributes(attributes);
        if (o == null) {
            return false;
        }
        return (boolean) o;
    }

    public <T extends Annotation> T chooseAnnotationType(Class<T> annotationClass) {
        return AnnotationUtils.findAnnotation(this.method, annotationClass);
    }

    public boolean hashAnnotationType(Class<? extends Annotation> annotationClass) {
        return chooseAnnotationType(annotationClass) != null;
    }

    public List<Mapping> selectColumnList() {
        return column().stream().filter(Objects::nonNull).filter(column -> !column.isSelectIgnore()).collect(Collectors.toList());
    }

    public List<Mapping> insertColumnList() {
        return column().stream().filter(Objects::nonNull).filter(column -> !column.isInsertIgnore()).collect(Collectors.toList());
    }

    public List<Mapping> updateColumnList() {
        return column().stream().filter(Objects::nonNull).filter(column -> !column.isUpdateIgnore()).collect(Collectors.toList());
    }

    public List<Mapping> column() {
        ArrayList<Mapping> list = new ArrayList<>();
        list.add(tableMetadata.getId());
        list.addAll(tableMetadata.getColumnMetaList());
        list.addAll(tableMetadata.getAuditorMap().values().stream().sorted(Comparator.comparingInt(s -> s.getType().ordinal())).collect(Collectors.toList()));
        list.add(tableMetadata.getLogic());
        return list;
    }
}
