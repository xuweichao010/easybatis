package com.xwc.open.easy.core.supports.impl;

import com.xwc.open.easy.core.supports.IdGenerateHandler;
import com.xwc.open.easy.core.supports.IdGenerateHandlerFactory;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2022/11/25 22:09
 */
public class DefaultIdGenerateHandlerFactory implements IdGenerateHandlerFactory {

    @Override
    public IdGenerateHandler getHandler(Class<? extends IdGenerateHandler> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }
}
