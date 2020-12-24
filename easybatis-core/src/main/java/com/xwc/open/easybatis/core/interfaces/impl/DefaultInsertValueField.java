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
        StringBuilder fieldValue = new StringBuilder();

        List<ParamMeta> entityList = methodMeta.getParamMetaList().stream().filter(ParamMeta::isEntity)
                .collect(Collectors.toList());
        if (entityList.size() == 1 && methodMeta.getParamMetaList().size() == 1) {
            fieldValue.append("(")
                    .append(methodMeta.insertColumnList().stream().map(column -> "#{" + column.getField() + "}")
                            .collect(Collectors.joining(", ")))
                    .append(")");
        } else if (entityList.size() == 1 && methodMeta.getParamMetaList().size() > 1) {
            ParamMeta paramMeta = entityList.get(0);
            return methodMeta.insertColumnList().stream().map(column -> "#{" + paramMeta.getParamName() + "." + column.getField() + "}")
                    .collect(Collectors.joining(","));
        } else {
            throw new EasyBatisException(" 发现了两个需要解析的entity数据");
        }
        return fieldValue.toString();
    }
}
