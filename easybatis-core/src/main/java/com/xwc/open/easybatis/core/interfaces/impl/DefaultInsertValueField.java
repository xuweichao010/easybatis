package com.xwc.open.easybatis.core.interfaces.impl;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.interfaces.InsertValueField;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.TableMeta;

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
        fieldSql.append("(");
        if (entityList.size() == 1 && methodMeta.getParamMetaList().size() == 1) {

            fieldSql.append(methodMeta.insertColumnList().stream().map(column -> this.fieldColumn(column.getField(), null))
                    .collect(Collectors.joining(", ")));

        } else if (entityList.size() == 1 && methodMeta.getParamMetaList().size() > 1) {
            ParamMeta paramMeta = entityList.get(0);
            fieldSql.append(methodMeta.insertColumnList().stream().map(column -> this.fieldColumn(column.getField(), paramMeta.getParamName()))
                    .collect(Collectors.joining(",")));
        } else {
            throw new EasyBatisException(" 发现了两个需要解析的entity数据");
        }
        fieldSql.append(")");
        return fieldSql.toString();
    }

    public String fieldColumn(String field, String prefix) {
        StringBuilder sb = new StringBuilder();
        sb.append("#{");
        if (prefix != null) {
            sb.append(prefix).append(".");
        }
        return sb.append(field).append("}").toString();
    }
}
