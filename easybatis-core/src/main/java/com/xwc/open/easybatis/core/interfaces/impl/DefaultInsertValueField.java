package com.xwc.open.easybatis.core.interfaces.impl;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.interfaces.InsertValueField;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.TableMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：
 */
public class DefaultInsertValueField implements InsertValueField {

    @Override
    public String apply(MethodMeta methodMeta) {
        StringBuilder fieldSql = new StringBuilder();
        List<ParamMeta> entityList = methodMeta.getParamMetaList().stream().filter(ParamMeta::isEntity)
                .collect(Collectors.toList());
        if (entityList.size() != 1) {
            throw new EasyBatisException("有 " + entityList.size() + " 个实体对象，导致无法创建SQL");
        }
        ParamMeta entityParam = entityList.get(0);
        if (methodMeta.getParamMetaList().size() == 1) {
            String valueSnippet = this.insertValueSnippet(methodMeta.insertColumnList(), null);
            if (entityParam.isBatch()) {
                return this.insertBatchForeach(valueSnippet, null);
            }
            return valueSnippet;
        } else if (methodMeta.getParamMetaList().size() > 1) {
            if (entityParam.isBatch()) {
                return this.insertBatchForeach(this.insertValueSnippet(methodMeta.insertColumnList(), null), entityParam.getParamName());
            } else {
                return this.insertValueSnippet(methodMeta.insertColumnList(), entityParam.getParamName());
            }
        }
        return fieldSql.toString();
    }

    private String fieldColumn(String field, String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append("#{");
        if (prefix != null) {
            sb.append(prefix).append(".");
        }
        return sb.append(field).append("}").toString();
    }

    public String insertValueSnippet(List<ColumnMeta> list, String prefix) {
        return "(" + list.stream().map(column -> this.fieldColumn(column.getField(), prefix)).collect(Collectors.joining(", ")) + ")";
    }
}
