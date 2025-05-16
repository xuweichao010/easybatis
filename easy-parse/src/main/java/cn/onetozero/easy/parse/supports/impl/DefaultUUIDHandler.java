package cn.onetozero.easy.parse.supports.impl;

import cn.onetozero.easy.annotations.supports.IdGenerateHandler;

import java.util.UUID;

/**
 * 类描述：使用UUID作为主键的生成策略
 * @author  徐卫超 (cc)
 * @since 2022/11/24 15:47
 */
public class DefaultUUIDHandler implements IdGenerateHandler {


    @Override
    public Object next(Object entity) {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
