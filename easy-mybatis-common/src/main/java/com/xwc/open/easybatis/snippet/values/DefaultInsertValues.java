package com.xwc.open.easybatis.snippet.values;

import com.xwc.open.easy.parse.model.parameter.CollectionEntityParameterAttribute;
import com.xwc.open.easy.parse.model.parameter.EntityParameterAttribute;
import com.xwc.open.easybatis.MyBatisSnippetUtils;
import com.xwc.open.easybatis.binding.BatisColumnAttribute;
import com.xwc.open.easybatis.supports.BatisPlaceholder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：当用户不定义的时候 框架默认处理 Insert 语句中 VALUES 语句后面的 (val1,val2,val3) 这部分语句的片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 14:07
 */
public class DefaultInsertValues implements InsertValuesSnippet {
    private BatisPlaceholder placeholder;

    public DefaultInsertValues(BatisPlaceholder placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String values(EntityParameterAttribute parameterAttribute, List<BatisColumnAttribute> batisColumnAttributes) {
        boolean isCollections = parameterAttribute instanceof CollectionEntityParameterAttribute;
        return isCollections ? insertCollection(parameterAttribute, batisColumnAttributes) : insertOne(batisColumnAttributes);
    }

    private String insertCollection(EntityParameterAttribute parameterAttribute,
                                    List<BatisColumnAttribute> batisColumnAttributes) {
        String collectionName = parameterAttribute.isMulti() ? parameterAttribute.getParameterName() : "collection";
        String contentValue = insertOne(batisColumnAttributes);
        return MyBatisSnippetUtils.foreachObject(parameterAttribute.getParameterName(), "index", collectionName,
                contentValue);
    }

    private String insertOne(List<BatisColumnAttribute> batisColumnAttributes) {
        return "(" + batisColumnAttributes.stream().map(placeholder::holder).collect(Collectors.joining(
                ",")) + ")";
    }
}
