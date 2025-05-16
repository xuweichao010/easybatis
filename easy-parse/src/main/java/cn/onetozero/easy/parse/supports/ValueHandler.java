package cn.onetozero.easy.parse.supports;

/**
 * 作者： 徐卫超
 * <p>
 * @since  2024-09-03 21:07
 * <p>
 * 描述：
 */
public interface ValueHandler<T> {
    T getValue(Object object);
}
