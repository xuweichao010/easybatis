package cn.onetozero.easy.parse.supports.impl;


import cn.onetozero.easy.parse.supports.ValueHandler;

/**
 * @author  徐卫超 (cc)
 * @since 2022/11/24 15:47
 */
public class DefaultHandler implements ValueHandler<Object> {
    @Override
    public Object getValue(Object object) {
        return object;
    }
}
