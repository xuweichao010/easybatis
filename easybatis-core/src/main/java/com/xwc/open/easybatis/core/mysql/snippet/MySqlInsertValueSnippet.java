
package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.model.TableMeta;
import com.xwc.open.easybatis.core.mysql.MysqlCommonsUtils;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.snippet.InsertValueSnippet;

import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/24
 * 描述：
 */
public class MySqlInsertValueSnippet implements InsertValueSnippet {

    private final PlaceholderBuilder placeholderBuilder;

    public MySqlInsertValueSnippet(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

    @Override
    public String apply(MethodMeta methodMeta) {
        ParamMapping entity = methodMeta.getParamMetaList().stream()
                .filter(ParamMapping::isEntity).findAny().orElseThrow(() -> new EasyBatisException("未发现实体对象"));
        String paramName = null;
        if (methodMeta.isMulti()) {
            paramName = entity.getParamName();
        } else if (entity.isBatch()) {
            paramName = "collection";
        }
        if (entity.isBatch()) {
            return insertBatch(paramName, methodMeta.getTableMetadata());
        } else {
            return insert(paramName, methodMeta.getTableMetadata());
        }
    }

    public String insertBatch(String param, TableMeta tableMeta) {
        return "<foreach item= 'item'  collection='" + param + "' separator=', '> "
                + insert("item", tableMeta)
                + " </foreach>";
    }

    public String insert(String paramName, TableMeta tableMeta) {
        return "(" + MysqlCommonsUtils.insertColumn(tableMeta).stream()
                .map(field -> placeholderBuilder.parameterHolder(field.getField(), paramName).getHolder())
                .collect(Collectors.joining(", ")) + ")";
    }


}
