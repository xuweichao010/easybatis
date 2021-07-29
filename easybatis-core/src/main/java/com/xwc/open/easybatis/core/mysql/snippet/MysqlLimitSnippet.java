package com.xwc.open.easybatis.core.mysql.snippet;

import com.xwc.open.easybatis.core.enums.ConditionType;
import com.xwc.open.easybatis.core.excp.EasyBatisException;
import com.xwc.open.easybatis.core.model.MethodMeta;
import com.xwc.open.easybatis.core.model.ParamMapping;
import com.xwc.open.easybatis.core.support.PlaceholderBuilder;
import com.xwc.open.easybatis.core.support.snippet.PageSnippet;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 作者：徐卫超 cc
 * 时间：2021/1/5
 * 描述：分页
 */
public class MysqlLimitSnippet implements PageSnippet {
    Set<ConditionType> pageCondition = Stream.of(ConditionType.OFFSET, ConditionType.START).collect(Collectors.toSet());

    private final PlaceholderBuilder placeholderBuilder;

    public MysqlLimitSnippet(PlaceholderBuilder placeholderBuilder) {
        this.placeholderBuilder = placeholderBuilder;
    }

    @Override
    public String apply(MethodMeta methodMeta) {
        List<ParamMapping> pageList = methodMeta.getParamMetaList().stream()
                .filter(item -> pageCondition.contains(item.getCondition())).collect(Collectors.toList());

        if (pageList.isEmpty()) return null;
        Map<ConditionType, ParamMapping> map = pageList.stream()
                .collect(Collectors.toMap(ParamMapping::getCondition, val -> val,
                        (val1, val2) -> {
                            throw new EasyBatisException("分页查询无法使重复的注解");
                        }
                ));
        String pageSql;
        ParamMapping mapping = map.get(ConditionType.OFFSET);
        if (mapping == null) {
            throw new EasyBatisException("未找到 @Offset 注解");
        } else {
            pageSql = mapping.getPlaceholderName().getHolder();
        }

        mapping = map.get(ConditionType.START);
        if (mapping != null) {
            pageSql = mapping.getPlaceholderName().getHolder() + ", " + pageSql;
        }
        return pageSql;
    }
}
