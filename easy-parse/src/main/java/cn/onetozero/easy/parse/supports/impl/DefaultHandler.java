package cn.onetozero.easy.parse.supports.impl;


import cn.onetozero.easy.parse.supports.ValueHandler;

/**
 * 作者： 徐卫超
 * <p>
 * 时间： 2024-09-03 21:10
 * <p>
 * 描述：
 */
public class DefaultHandler implements ValueHandler<Object> {
    @Override
    public Object getValue(Object object) {
        return object;
    }
}
