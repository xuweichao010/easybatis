package com.xwc.open.easy.core.supports.impl;

import com.xwc.open.easy.core.supports.IdTypeHandler;

import java.util.UUID;

/**
 * 类描述：使用UUID作为主键的生成策略
 * 作者：徐卫超 (cc)
 * 时间 2022/11/24 15:47
 */
public class DefaultUUIDHandler implements IdTypeHandler {
    @Override
    public String next() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
