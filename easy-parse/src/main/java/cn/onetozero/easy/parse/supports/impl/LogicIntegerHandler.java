package cn.onetozero.easy.parse.supports.impl;


import cn.onetozero.easy.parse.supports.ValueHandler;

/**
 * 作者： 徐卫超
 * <p>
 * @since  2024-09-03 21:10
 * <p>
 * 描述：
 */
public class LogicIntegerHandler implements ValueHandler<Integer> {
    @Override
    public Integer getValue(Object object) {
        return Integer.parseInt(String.valueOf(object));
    }
}
