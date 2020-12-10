package com.xwc.open.easybatis.support;

import com.xwc.open.easybatis.anno.condition.Count;
import com.xwc.open.easybatis.anno.condition.Distinct;
import com.xwc.open.easybatis.anno.condition.Join;
import com.xwc.open.easybatis.anno.condition.PrimaryKey;
import lombok.Builder;
import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.List;


@Data
@Builder
public class MethodMeta {

    private String methodName;

    /**
     * SQL操作的类型
     */
    private Annotation sqlCommond;

    /**
     * 是否是Join语句
     */
    private Join join;

    /**
     * 是否属统计总数方法
     */
    private Count count;

    /**
     * 是否需要对结果进行去重
     */
    private Distinct distinct;

    /**
     * 是否是主键查询
     */
    private PrimaryKey key;


    /**
     * 关联的表的实体的定义信息
     */
    TableMeta tableMetadata;
    /**
     * 方法上的注解定义信息
     */
    List<Annotation> methodAnntaion;

    /**
     * 参数有的定义信息
     */
    List<ParamMeta> paramMetaData;

}
