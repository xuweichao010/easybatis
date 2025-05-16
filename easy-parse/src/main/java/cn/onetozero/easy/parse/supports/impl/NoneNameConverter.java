package cn.onetozero.easy.parse.supports.impl;

import cn.onetozero.easy.parse.supports.NameConverter;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/24 15:59
 */
public class NoneNameConverter implements NameConverter {
    @Override
    public String convert(String name) {
        return name;
    }
}
