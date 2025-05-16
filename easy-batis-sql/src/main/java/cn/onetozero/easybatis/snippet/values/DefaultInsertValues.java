package cn.onetozero.easybatis.snippet.values;

import cn.onetozero.easy.parse.model.parameter.CollectionEntityParameterAttribute;
import cn.onetozero.easy.parse.model.parameter.EntityParameterAttribute;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：当用户不定义的时候 框架默认处理 Insert 语句中 VALUES 语句后面的 (val1,val2,val3) 这部分语句的片段
 * @author  徐卫超 (cc)
 * @since 2023/1/16 14:07
 */
public class DefaultInsertValues implements InsertValuesSnippet {
    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultInsertValues(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
    }

    @Override
    public String values(EntityParameterAttribute parameterAttribute, List<BatisColumnAttribute> batisColumnAttributes) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        boolean isCollections = parameterAttribute instanceof CollectionEntityParameterAttribute;
        return isCollections ? insertCollection(parameterAttribute, batisColumnAttributes) :
                insertOne(batisColumnAttributes, batisPlaceholder);
    }

    private String insertCollection(EntityParameterAttribute parameterAttribute,
                                    List<BatisColumnAttribute> batisColumnAttributes) {
        BatisPlaceholder batisPlaceholder = this.sourceGenerator.getBatisPlaceholder();
        String collectionName = parameterAttribute.isMulti() ? parameterAttribute.getParameterName() : "collection";
        String contentValue = insertOne(batisColumnAttributes, batisPlaceholder);
        return MyBatisSnippetUtils.foreachObject(parameterAttribute.getParameterName(), "index", collectionName,
                contentValue);
    }

    private String insertOne(List<BatisColumnAttribute> batisColumnAttributes, BatisPlaceholder batisPlaceholder) {
        return " (" + batisColumnAttributes.stream().map(batisPlaceholder::holder).collect(Collectors.joining(
                ",")) + ")";
    }
}
