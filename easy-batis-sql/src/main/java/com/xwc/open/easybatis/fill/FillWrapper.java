package com.xwc.open.easybatis.fill;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/3 11:17
 */
public interface FillWrapper {

    Object getValue(String name);
    void setValue(String name, Object value);

}
