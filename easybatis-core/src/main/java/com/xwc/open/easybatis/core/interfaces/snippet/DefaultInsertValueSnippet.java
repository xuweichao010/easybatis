package com.xwc.open.easybatis.core.interfaces.snippet;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.ColumnMeta;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：
 */
public class DefaultInsertValueSnippet implements InsertValueSnippet {

    @Override
    public String apply(MethodMeta methodMeta) {
        List<ParamMeta> paramList = methodMeta.getParamMetaList();
        List<ParamMeta> entityList = methodMeta.getParamMetaList().stream().filter(ParamMeta::isEntity)
                .collect(Collectors.toList());
        ParamMeta entityParam = entityList.get(0);
        if (methodMeta.entityParam() == null) {
            throw new EasyBatisException("有 " + entityList.size() + " 个实体对象，导致无法创建SQL");
        }
        // 处理一个参数
        if (paramList.size() == 1) {
            String valueSnippet = this.insertValueSnippet(methodMeta.insertColumnList(), entityParam.isList()?"item":null);
            if (entityParam.isList()) {
                return this.insertBatchForeach(valueSnippet, null);
            }
            return valueSnippet;
        } else { // 处理多个参数
            if (entityParam.isList()) {
                return this.insertBatchForeach(this.insertValueSnippet(methodMeta.insertColumnList(), "item"), entityParam.getParamName());
            } else {
                return this.insertValueSnippet(methodMeta.insertColumnList(), entityParam.getParamName());
            }
        }
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
