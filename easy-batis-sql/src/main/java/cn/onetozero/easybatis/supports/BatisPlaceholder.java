package cn.onetozero.easybatis.supports;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/16 15:31
 */
public interface BatisPlaceholder {

    String holder(BatisColumnAttribute columnAttribute);

    String path(BatisColumnAttribute columnAttribute);

    String join(String[] path);
}
