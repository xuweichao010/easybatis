package cn.onetozero.easy.parse.supports;

/**
 * @author  徐卫超 (cc)
 * @since 2022/11/24 15:47
 */
public interface ValueHandler<T> {
    T getValue(Object object);
}
