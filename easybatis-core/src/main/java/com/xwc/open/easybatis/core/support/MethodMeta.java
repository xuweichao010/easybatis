package com.xwc.open.easybatis.core.support;

import com.xwc.open.easybatis.core.commons.AnnotationUtils;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;
import lombok.Data;
import org.apache.ibatis.mapping.SqlCommandType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Data
public class MethodMeta {

    private String methodName;
    /**
     * SQL操作的类型
     */
    private SqlCommandType sqlCommand;

    private boolean dynamic;
    /**
     * 关联的表的实体的定义信息
     */
    TableMeta tableMetadata;

    private Method method;
    /**
     * 参数有的定义信息
     */
    List<ParamMeta> paramMetaList;

    public boolean hashCondition() {
        return this.paramMetaList.stream().anyMatch(ParamMeta::isCondition);
    }


    public <T extends Annotation> T chooseAnnotationType(Class<T> annotationClass) {
        return AnnotationUtils.findAnnotation(this.method, annotationClass);
    }

    public boolean hashAnnotationType(Class<? extends Annotation> annotationClass) {
        return chooseAnnotationType(annotationClass) != null;
    }

    public List<ColumnMeta> selectColumnList() {
        ArrayList<ColumnMeta> list = new ArrayList<>();
        list.add(tableMetadata.getId());
        list.addAll(tableMetadata.getColumnMetaList());
        list.addAll(tableMetadata.getAuditorList());
        list.add(tableMetadata.getLogic());
        return list.stream().filter(Objects::nonNull).filter(column -> !column.isSelectIgnore()).collect(Collectors.toList());
    }

    public List<ColumnMeta> insertColumnList() {
        return column().stream().filter(Objects::nonNull).filter(column -> !column.isInsertIgnore()).collect(Collectors.toList());
    }

    public List<ColumnMeta> updateColumnList() {
        return column().stream().filter(Objects::nonNull).filter(column -> !column.isUpdateIgnore()).collect(Collectors.toList());
    }

    public List<ColumnMeta> column() {
        ArrayList<ColumnMeta> list = new ArrayList<>();
        list.add(tableMetadata.getId());
        list.addAll(tableMetadata.getColumnMetaList());
        list.addAll(tableMetadata.getAuditorList());
        list.add(tableMetadata.getLogic());
        return list;
    }
}
