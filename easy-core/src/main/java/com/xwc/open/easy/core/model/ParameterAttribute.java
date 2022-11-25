package com.xwc.open.easy.core.model;

/**
 * 类描述：用于存储方法中的参数的各种信息
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 14:23
 */
public class ParameterAttribute {
    /**
     * 参数存在的位置
     */
    private int index;

    /**
     * 参数是否是虚拟的参数，虚拟参数就是实际上没有的参数，为了进行相关业务把参数添加进
     */
    private int virtual;

}
