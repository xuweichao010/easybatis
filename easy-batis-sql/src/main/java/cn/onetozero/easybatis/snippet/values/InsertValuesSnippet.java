package cn.onetozero.easybatis.snippet.values;

import cn.onetozero.easy.parse.model.parameter.EntityParameterAttribute;
import cn.onetozero.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：用于处理 Insert 语句中 VALUES 语句后面的 (val1,val2,val3) 这部分语句的片段
 * 作者：徐卫超 (cc)
 * 时间 2023/1/16 14:07
 */
public interface InsertValuesSnippet {

    String values(EntityParameterAttribute parameterAttribute, List<BatisColumnAttribute> batisColumnAttributes);
}
