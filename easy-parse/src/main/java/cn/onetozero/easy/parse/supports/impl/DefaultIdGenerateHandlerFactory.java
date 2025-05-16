package cn.onetozero.easy.parse.supports.impl;

import cn.onetozero.easy.annotations.supports.IdGenerateHandler;
import cn.onetozero.easy.parse.supports.IdGenerateHandlerFactory;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2022/11/25 22:09
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
