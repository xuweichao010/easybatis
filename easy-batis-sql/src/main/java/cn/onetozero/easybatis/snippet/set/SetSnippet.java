package cn.onetozero.easybatis.snippet.set;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * @author  徐卫超 (cc)
 * @since 2023/2/1 16:30
 */
public interface SetSnippet {

    String set(List<BatisColumnAttribute> attributes);
}
