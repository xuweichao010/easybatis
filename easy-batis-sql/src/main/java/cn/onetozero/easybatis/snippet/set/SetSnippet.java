package cn.onetozero.easybatis.snippet.set;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

import java.util.List;

/**
 * 类描述：
 * 作者：徐卫超 (cc)
 * 时间 2023/2/1 16:30
 */
public interface SetSnippet {

    String set(List<BatisColumnAttribute> attributes);
}
