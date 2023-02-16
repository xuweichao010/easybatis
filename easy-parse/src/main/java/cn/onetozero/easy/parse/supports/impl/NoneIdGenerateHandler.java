package cn.onetozero.easy.parse.supports.impl;

import cn.onetozero.easy.parse.supports.IdGenerateHandler;

/**
 * 类描述：一个空的实现 主要是为了Id注解的参数有默认选项
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:46
 */
public class NoneIdGenerateHandler implements IdGenerateHandler {

    @Override
    public Object next(Object entity) {
        return null;
    }
}
