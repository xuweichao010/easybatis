package cn.onetozero.easy.parse.supports.impl;


import cn.onetozero.easy.parse.supports.ValueHandler;

/**
 * @author  徐卫超 (cc)
 * @since 2022/11/24 15:47
 */
public class LogicIntegerHandler implements ValueHandler<Integer> {
    @Override
    public Integer getValue(Object object) {
        return Integer.parseInt(String.valueOf(object));
    }
}
