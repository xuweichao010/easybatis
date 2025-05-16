package cn.onetozero.easybatis.fill;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/3 11:17
 */
public interface FillWrapper {

    Object getValue(String name);
    void setValue(String name, Object value);

}
