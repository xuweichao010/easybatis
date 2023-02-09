package com.xwc.open.easybatis.snippet.page;

import com.xwc.open.easybatis.annotaions.page.Limit;
import com.xwc.open.easybatis.annotaions.page.Offset;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.exceptions.CheckException;
import com.xwc.open.easybatis.supports.AbstractBatisSourceGenerator;
import com.xwc.open.easybatis.supports.BatisPlaceholder;

import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/1/31 16:14
 */
public class DefaultPageSnippet implements PageSnippet {

    private static final Set<Class<? extends Annotation>> pageAnnotationTypes = Stream
            .of(Limit.class, Offset.class)
            .collect(Collectors.toSet());

    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultPageSnippet(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }


    @Override
    public String page(List<BatisColumnAttribute> batisColumnAttributes) {
        List<BatisColumnAttribute> pageBatisColumns = batisColumnAttributes
                .stream().filter(attribute -> chooseOrder(attribute) != null)
                .sorted(Comparator.comparingInt(BatisColumnAttribute::getIndex)).collect(Collectors.toList());
        if (pageBatisColumns.size() > 2) {
            throw new CheckException("分页参数过多无法解析");
        } else if (pageBatisColumns.size() == 0) {
            return "";
        }
        return doPage(pageBatisColumns);
    }


    private String doPage(List<BatisColumnAttribute> pageBatisColumns) {
        BatisPlaceholder batisPlaceholder = sourceGenerator.getBatisPlaceholder();
        Map<Class<? extends Annotation>, BatisColumnAttribute> pageMap = pageBatisColumns.stream()
                .collect(Collectors.toMap(key -> chooseOrder(key).annotationType(), val -> val));
        BatisColumnAttribute limitAttribute = pageMap.get(Limit.class);
        String sqlSnippet = "";
        if (limitAttribute == null) {
            throw new CheckException("缺少Limit注解");
        }
        sqlSnippet += " LIMIT " + batisPlaceholder.holder(limitAttribute);
        BatisColumnAttribute offsetAttribute = pageMap.get(Offset.class);
        if (offsetAttribute != null) {
            sqlSnippet += " OFFSET " + batisPlaceholder.holder(offsetAttribute);
        }
        return sqlSnippet;
    }

    private Annotation chooseOrder(BatisColumnAttribute attribute) {
        for (Class<? extends Annotation> orderAnnotationType : pageAnnotationTypes) {
            Annotation annotation = attribute.findAnnotation(orderAnnotationType);
            if (annotation != null) {
                return annotation;
            }
        }
        return null;
    }


}
