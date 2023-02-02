package com.xwc.open.easybatis.snippet.set;

import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.annotaions.set.SetParam;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;
import com.xwc.open.easybatis.supports.ColumnPlaceholder;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 16:30
 */
public class DefaultSetSnippet implements SetSnippet {


    private BatisPlaceholder placeholder;

    private ColumnPlaceholder columnPlaceholder;

    public DefaultSetSnippet(BatisPlaceholder placeholder, ColumnPlaceholder columnPlaceholder) {
        this.placeholder = placeholder;
        this.columnPlaceholder = columnPlaceholder;
    }

    @Override
    public String set(List<BatisColumnAttribute> attributes) {
        List<BatisColumnAttribute> setParams = attributes.stream().filter(attribute -> {
            SetParam annotation = attribute.findAnnotation(SetParam.class);
            if (annotation != null) {
                return true;
            } else if (attribute.getSqlCommandType() == SqlCommandType.UPDATE && attribute.annotations().isEmpty()) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        return MyBatisSnippetUtils.set(doSet(setParams));
    }

    public String doSet(List<BatisColumnAttribute> setParams) {
        return setParams.stream().map(set -> {
            SetParam annotation = set.findAnnotation(SetParam.class);
            String setSql = columnPlaceholder.holder(set.useColumn(annotation)) + "=" + placeholder.holder(set) + ",";
            if (set.useDynamic(annotation)) {
                setSql = MyBatisSnippetUtils.ifNonNullObject(placeholder.path(set), setSql);
            }
            return setSql;
        }).collect(Collectors.joining(" "));
    }
}
