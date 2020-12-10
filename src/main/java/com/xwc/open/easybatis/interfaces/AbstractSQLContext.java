package com.xwc.open.easybatis.interfaces;

import com.xwc.open.easybatis.interfaces.condition.CompareCondition;
import com.xwc.open.easybatis.interfaces.condition.NullCondition;
import com.xwc.open.easybatis.support.MethodMeta;
import com.xwc.open.easybatis.support.ParamMeta;
import com.xwc.open.easybatis.support.TableMeta;
import com.xwc.open.easybatis.support.table.Column;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建人：徐卫超 CC
 * 时间：2020/9/19 14:30
 * 备注：
 */
public abstract class AbstractSQLContext implements SQLContext {
    private List<QueryCondition> list;

    public AbstractSQLContext(List<QueryCondition> list) {
        this.list = list;
    }

    public AbstractSQLContext() {
        list = new ArrayList<>();
        list.add(new CompareCondition());
        list.add(new NullCondition());
    }

    public String selectColunm(TableMeta metadata) {
        ArrayList<Column> list = new ArrayList<>();
        list.addAll(metadata.getColumnList());
        list.addAll(metadata.getAuditorList());
        return list.stream()
                .filter(column -> !column.isSelectIgnore())
                .map(column -> "`" + column.getColumn() + "`").collect(Collectors.joining(","));
    }

    public String queryCondition(MethodMeta metadata, boolean isDynamic) {
        return metadata.getParamMetaData().stream().filter(paramMetaData -> !paramMetaData.isCustom())
                .map(paramMetaData -> mapCondition(paramMetaData, isDynamic))
                .filter(StringUtils::hasText).collect(Collectors.joining(" AND "))
                + " " +
                metadata.getParamMetaData().stream()
                        .filter(ParamMeta::isCustom)
                        .map(paramMetaData -> mapCondition(paramMetaData, isDynamic))
                        .filter(StringUtils::hasText).collect(Collectors.joining(" "));

    }

    private String mapCondition(ParamMeta metadata, boolean isDynamic) {
        for (QueryCondition queryCondition : list) {
            String condition = queryCondition.apply(metadata, isDynamic);
            if (StringUtils.hasText(condition)) {
                return condition;
            }
        }
        return null;
    }


}
