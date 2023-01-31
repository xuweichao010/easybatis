package com.xwc.open.easybatis.snippet.page;

import com.xwc.open.easybatis.annotaions.page.Limit;
import com.xwc.open.easybatis.annotaions.page.Offset;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;

import java.lang.annotation.Annotation;
import java.util.List;
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


    private BatisPlaceholder placeholder;

    public DefaultPageSnippet(BatisPlaceholder placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String page(List<BatisColumnAttribute> batisColumnAttributes) {
        return null;
    }
}
