package cn.onetozero.easy.parse.supports;

import cn.onetozero.easy.annotations.supports.IdGenerateHandler;

/**
 * 类描述：用于创建ID生成器的工厂 他决定了 @link com.xwc.open.easy.core.supports.IdGenerateHandler 的实例化方式
 * @author  徐卫超 (cc)
 * @since 2022/11/25 22:05
 */
public interface IdGenerateHandlerFactory {

    /**
     * @param clazz id生成器的实例对象名称
     * @return 返回一个Id生成器
     */
    IdGenerateHandler getHandler(Class<? extends IdGenerateHandler> clazz);
}
