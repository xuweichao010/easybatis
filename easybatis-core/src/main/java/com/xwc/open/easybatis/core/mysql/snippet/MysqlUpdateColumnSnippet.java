package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.model.Placeholder;
import com.xwc.open.easybatis.core.model.table.Mapping;
import com.xwc.open.easybatis.core.mysql.MysqlCommonsUtils;
import com.xwc.open.easybatis.core.support.MyBatisOrSqlTemplate;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.snippet.UpdateSetSnippet;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新列片段SQL构建
 */
public class MysqlUpdateColumnSnippet implements UpdateSetSnippet, MyBatisOrSqlTemplate {
    private final PlaceholderBuilder placeholderBuilder;

    public MysqlUpdateColumnSnippet(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

    public String apply(MethodMeta methodMeta) {
        ParamMapping entity = methodMeta.getParamMetaList().stream()
                .filter(ParamMapping::isEntity).findAny().orElse(null);
        List<ParamMapping> collect = methodMeta.getParamMetaList().stream()
                .filter(item -> item.getCondition() == ConditionType.SET_PARAM)
                .collect(Collectors.toList());
        if (entity != null && !collect.isEmpty()) {
            throw new EasyBatisException("实体和paramSet不能同时使用");
        }
        if (entity == null && collect.isEmpty()) {
            throw new EasyBatisException("方法解析为Update时出错");
        }
        Boolean dynamic = methodMeta.optionalBooleanAttributes("dynamic");
        if (entity != null) {
            String prefix = methodMeta.isMulti() ? entity.getParamName() : null;
            return MysqlCommonsUtils.updateColumn(methodMeta.getTableMetadata()).stream()
                    .map(column -> setParam(column, prefix, dynamic))
                    .collect(Collectors.joining(" "));
        } else {
            return collect.stream()
                    .map(mapping -> setParam(mapping, dynamic)).collect(Collectors.joining(" "));
        }

    }

    public String setParam(Mapping mapping, String prefix, boolean isDynamic) {
        Placeholder columnHolder = placeholderBuilder.columnHolder(null, mapping.getColumn());
        Placeholder parameterHolder = placeholderBuilder.parameterHolder(mapping.getField(), prefix);

        String sql = columnHolder.getHolder() + " = " + parameterHolder.getHolder() +",";
        if (isDynamic) {
            return dynamic(parameterHolder.getName(), sql);
        }
        return sql;
    }

    public String setParam(ParamMapping mapping, boolean isDynamic) {
        Placeholder columnHolder = placeholderBuilder.columnHolder(mapping.getAlias(), mapping.getColumnName());
        Placeholder parameterHolder = placeholderBuilder.parameterHolder(mapping.getParamName(), null);

        String sql = columnHolder.getHolder() + " = " + parameterHolder.getHolder() + ",";
        if (isDynamic) {
            return dynamic(parameterHolder.getName(), sql);
        }
        return sql;
    }

    public String dynamic(String param, String sql) {
        return "<if test='" + param + " != null'> " + sql + "</if>";
    }


}
