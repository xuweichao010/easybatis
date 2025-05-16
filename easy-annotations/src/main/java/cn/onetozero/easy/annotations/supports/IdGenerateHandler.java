package cn.onetozero.easy.annotations.supports;

/**
 * 类描述：主键生成处理器
 * @author  徐卫超 (cc)
 * @since 2022/11/24 15:46
 */
public interface IdGenerateHandler {

    /**
     * 获取一个唯一的主键
     *
     * @param entity 需要获取主键的对象
     * @return 返回一个主键
     */
    Object next(Object entity);
}
