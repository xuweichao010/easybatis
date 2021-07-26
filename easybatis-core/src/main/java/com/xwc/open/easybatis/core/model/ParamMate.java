package com.xwc.open.easybatis.core.model;

import lombok.Data;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 作者：Clay(徐卫超 )
 * 时间：2021/6/23
 * 描述：
 */

@Data
public class ParamMate {
    public static final int TYPE_KEY = 1; // 主键
    public static final int TYPE_ENTITY = 2; // 实体
    public static final int TYPE_CUSTOM_ENTITY = 3; //自定义实体
    public static final int TYPE_PARAM = 4;  //NONE
    public static final int TYPE_LOGIC = 5; //逻辑删除
    public static final int TYPE_AUDITOR = 6; // 审计
    /**
     * 参数类型 1-主键 2-实体 3-none
     */
    private int type;

    /**
     * 参数名称
     */
    private String paramName;
    /**
     * 子节点信息
     */
    private List<ParamMate> children;

    /**
     * 条件注解
     */
    private Annotation annotation;

    private Class<?> sourceClass;


    public static ParamMate builder(String paramName, Annotation annotation) {
        return builder(paramName, TYPE_PARAM, annotation);
    }

    public static ParamMate builder(String paramName, int type) {
        return builder(paramName, type, null);
    }

    public static ParamMate builder(String paramName, Integer type, Annotation annotation) {
        ParamMate tar = new ParamMate();
        tar.setParamName(paramName);
        tar.type = type;
        tar.annotation = annotation;
        return tar;
    }
}
