package cn.onetozero.easybatis.supports;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/1/16 15:34
 */
public class MybatisPlaceholder implements BatisPlaceholder {
    private static final String OPEN = "#{";

    private static final String CLOSE = "}";

    @Override
    public String holder(BatisColumnAttribute batisColumnAttribute) {
        return OPEN + path(batisColumnAttribute) + CLOSE;

    }

    @Override
    public String path(BatisColumnAttribute columnAttribute) {
        if (columnAttribute.isMulti()) {
            return join(columnAttribute.getPath());
        } else {
            return columnAttribute.getParameterName();
        }
    }

    @Override
    public String join(String[] path) {
        return String.join(".", path);
    }
}
