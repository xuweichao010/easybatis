package cn.onetozero.easybatis.snippet.set;

import cn.onetozero.easybatis.annotaions.set.SetParam;
import cn.onetozero.easybatis.supports.AbstractBatisSourceGenerator;
import cn.onetozero.easybatis.supports.BatisPlaceholder;
import cn.onetozero.easybatis.supports.SqlPlaceholder;
import cn.onetozero.easybatis.MyBatisSnippetUtils;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;
import org.apache.ibatis.mapping.SqlCommandType;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 16:30
 */
public class DefaultSetSnippet implements SetSnippet {


    private final AbstractBatisSourceGenerator sourceGenerator;

    public DefaultSetSnippet(AbstractBatisSourceGenerator sourceGenerator) {
        this.sourceGenerator = sourceGenerator;
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
        BatisPlaceholder batisPlaceholder = sourceGenerator.getBatisPlaceholder();
        SqlPlaceholder sqlPlaceholder = sourceGenerator.getSqlPlaceholder();
        return setParams.stream().map(set -> {
            SetParam annotation = set.findAnnotation(SetParam.class);
            String setSql = sqlPlaceholder.holder(set.useColumn(annotation)) + "=" + batisPlaceholder.holder(set) + ",";
            if (set.useDynamic(annotation)) {
                setSql = MyBatisSnippetUtils.ifNonNullObject(batisPlaceholder.path(set), setSql);
            }
            return setSql;
        }).collect(Collectors.joining(" "));
    }
}
