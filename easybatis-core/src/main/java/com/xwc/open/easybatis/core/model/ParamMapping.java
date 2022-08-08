package com.xwc.open.easybatis.core.model;

import lombok.Data;

import java.util.List;

/**
 * 作者：徐卫超 cc 时间：2020/9/9 描述：属性元数据
 */

@Data
public class ParamMapping {

    /**
     * 是不是实体
     */
    private boolean entity;

    /**
     * 是不是批量操作
     */
    private boolean batch;


    /**
     * 属性名
     */
    private String paramName;

    /**
     *
     */
    private String methodParamName;

    /**
     * 列名
     */
    private String columnName;

    /**
     * 占位符
     */
    private Placeholder placeholderName;

    /**
     * 属性别名
     */
    private String alias;
    /**
     * 是否是动态语句
     */
    private boolean dynamic;

    /**
     * 组合条件 例如： LIMIT  BETWEEN 等语法需要用到该属性
     */
    private List<ParamMapping> composition;

    /**
     * 条件
     */
    private ConditionType condition;


    public static ParamMapping convert(String paramName, String columnName, Placeholder placeholderName, String alias,
                                       boolean dynamic, ConditionType condition, String methodParam) {
        ParamMapping tar = new ParamMapping();
        tar.paramName = paramName;
        tar.columnName = columnName;
        tar.placeholderName = placeholderName;
        tar.alias = (alias == null || alias.isEmpty() ? null : alias);
        tar.dynamic = dynamic;
        tar.condition = condition;
        tar.methodParamName = methodParam;
        return tar;
    }

    public static ParamMapping convertEntity(String paramName, boolean isBatch) {
        ParamMapping tar = new ParamMapping();
        tar.paramName = paramName;
        tar.entity = true;
        tar.batch = isBatch;
        tar.methodParamName = paramName;
        return tar;
    }

    public boolean isMethodParam() {
        return this.methodParamName != null && !this.methodParamName.isEmpty();
    }
}
