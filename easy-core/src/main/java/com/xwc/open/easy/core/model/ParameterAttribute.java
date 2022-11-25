package com.xwc.open.easy.core.model;

/**
 * 类描述：用于存储方法中的参数的各种信息 提供给后面的构建器来使用
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:23
 */
public class ParameterAttribute {
    /**
     * 参数存在的位置
     */
    private int index;
    /**
     * 属性名称
     */
    private String parameterName;
    /**
     * 属性路径 从根路径开始
     */
    private String[] path;

}
