package cn.onetozero.easy.parse.supports.impl;

import cn.onetozero.easy.parse.supports.NameConverter;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:59
 */
public class NoneNameConverter implements NameConverter {
    @Override
    public String convert(String name) {
        return name;
    }
}
