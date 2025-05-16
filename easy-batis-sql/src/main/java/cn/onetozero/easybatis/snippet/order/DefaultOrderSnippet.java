package cn.onetozero.easybatis.snippet.order;

import cn.onetozero.easy.parse.model.OperateMethodMeta;
import cn.onetozero.easy.parse.utils.StringUtils;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easy.annotations.order.Asc;
import cn.onetozero.easy.annotations.order.Desc;
import cn.onetozero.easy.annotations.order.OrderBy;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/31 11:28
 */
public class DefaultOrderSnippet implements OrderSnippet {

    public static final String ORDER_BY = " ORDER BY ";

    private static final Set<Class<? extends Annotation>> orderAnnotationTypes = Stream
            .of(Asc.class, Desc.class)
            .collect(Collectors.toSet());

    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultOrderSnippet(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
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
        BatisPlaceholder batisPlaceholder = sourceGenerator.getBatisPlaceholder();
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
