package com.xwc.open.easybatis.snippet.order;

import com.xwc.open.easy.parse.model.OperateMethodMeta;
import com.xwc.open.easy.parse.utils.StringUtils;
import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.order.Asc;
import com.xwc.open.easybatis.annotaions.order.Desc;
import com.xwc.open.easybatis.annotaions.order.OrderBy;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/31 11:28
 */
public class DefaultOrderSnippet implements OrderSnippet {

    public String ORDER_BY = " ORDER BY ";

    private static final Set<Class<? extends Annotation>> orderAnnotationTypes = Stream
            .of(Asc.class, Desc.class)
            .collect(Collectors.toSet());

    private final BatisPlaceholder batisPlaceholder;

    public DefaultOrderSnippet(BatisPlaceholder batisPlaceholder) {
        this.batisPlaceholder = batisPlaceholder;
    }

    @Override
    public String order(OperateMethodMeta operateMethodMeta, List<BatisColumnAttribute> batisColumnAttributes) {
        return doOrder(operateMethodMeta, batisColumnAttributes);
    }

    public String doOrder(OperateMethodMeta operateMethodMeta, List<BatisColumnAttribute> batisColumnAttributes) {
        OrderBy annotation = operateMethodMeta.findAnnotation(OrderBy.class);
        if (annotation != null && StringUtils.hasText(annotation.value())) {
            return ORDER_BY + annotation.value();
        } else {
            return orderAttribute(batisColumnAttributes);
        }
    }

    private String orderAttribute(List<BatisColumnAttribute> batisColumnAttributes) {
        List<BatisColumnAttribute> orderBatisColumn = batisColumnAttributes
                .stream().filter(attribute -> chooseOrder(attribute) != null)
                .sorted(Comparator.comparingInt(BatisColumnAttribute::getIndex)).collect(Collectors.toList());
        if (orderBatisColumn.isEmpty()) {
            return "";
        } else {
            String orderSnippet = orderBatisColumn.stream().map(attribute -> {
                Annotation annotation = chooseOrder(attribute);
                String column = attribute.useColumn(annotation);
                boolean orderDynamic = attribute.useDynamic(annotation);
                String order = annotation instanceof Asc ? "ASC" : "DESC";
                String columnOrder = column + " " + order + ", ";
                if (orderDynamic) {
                    return MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(attribute), columnOrder);
                }
                return columnOrder;
            }).collect(Collectors.joining(" "));
            return MyBatisSnippetUtils.trimSuffixOverrides(ORDER_BY, ",", orderSnippet);
        }
    }

    private Annotation chooseOrder(BatisColumnAttribute attribute) {
        for (Class<? extends Annotation> orderAnnotationType : orderAnnotationTypes) {
            Annotation annotation = attribute.findAnnotation(orderAnnotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }


}
