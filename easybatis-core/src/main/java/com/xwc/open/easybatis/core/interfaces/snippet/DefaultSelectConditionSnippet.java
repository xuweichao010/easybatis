package com.xwc.open.easybatis.core.interfaces.snippet;

import com.xwc.open.easybatis.core.commons.StringUtils;
import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.interfaces.condition.CompareCondition;
import com.xwc.open.easybatis.core.interfaces.condition.QueryCondition;
import com.xwc.open.easybatis.core.support.MethodMeta;
import com.xwc.open.easybatis.core.support.ParamMeta;
import com.xwc.open.easybatis.core.support.table.IdMeta;
import com.xwc.open.easybatis.core.support.table.LoglicColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 作者：徐卫超 cc
 * 时间：2020/12/25
 * 描述：更新语句更新条件SQL片段构建
 */
public class DefaultSelectConditionSnippet implements SelectConditionSnippet {
    protected List<QueryCondition> conditionList = new ArrayList<>();

    public DefaultSelectConditionSnippet() {
        this.conditionList = Stream.of(new CompareCondition()).collect(Collectors.toList());
    }

    public String apply(MethodMeta methodMeta) {
        List<ParamMeta> paramMetaList = methodMeta.getParamMetaList();
        List<ParamMeta> queryList = paramMetaList.stream().filter(paramMeta->!paramMeta.isIgnore()).collect(Collectors.toList());
        // 处理参数为主键类型的情况
        List<ParamMeta> list = new ArrayList<>();
        boolean multi = paramMetaList.size() != queryList.size();
        if (queryList.size() == 1) {
            if (methodMeta.keyParam() != null) {
                IdMeta id = methodMeta.getTableMetadata().getId();
                list.add(ParamMeta.builder(id.getColumn(), id.getField(), ConditionType.EQUAL));
            } else if (paramMetaList.get(0).isMultiCondition()) {
                list.addAll(methodMeta.getParamMetaList().get(0).getChildList());
            } else {
                list.addAll(methodMeta.getParamMetaList());
            }
        } else if (paramMetaList.size() > 1) {
            paramMetaList.forEach(paramMeta -> {
                if (paramMeta.isMultiCondition()) {
                    list.addAll(paramMeta.getChildList());
                } else {
                    list.add(paramMeta);
                }
            });
            multi = true;
        }
        if (methodMeta.getTableMetadata().getLogic() != null) {
            LoglicColumn logic = methodMeta.getTableMetadata().getLogic();
            list.add(ParamMeta.builder(logic.getColumn(), logic.getField()));
        }
        // 处理方法上的对象参数条件
        return listCondition(list, multi);
    }

    public String listCondition(List<ParamMeta> list, boolean multi) {
        String queryCondition = list.stream()
                .map(condition -> mapCondition(condition, multi))
                .filter(StringUtils::hasText).collect(Collectors.joining(" ")).trim();
        if (queryCondition.startsWith("AND")) {
            return queryCondition.substring("AND".length());
        }
        return queryCondition;
    }


    private String mapCondition(ParamMeta metadata, boolean multi) {
        for (QueryCondition queryCondition : conditionList) {
            String condition = queryCondition.apply(metadata, multi);
            if (StringUtils.hasText(condition)) {
                return condition;
            }
        }
        return null;
    }
}
