package cn.onetozero.easybatis.snippet.conditional;

import cn.onetozero.easybatis.binding.BatisColumnAttribute;

/**
 * 类描述：单个过滤语法
 * 作者：徐卫超 (cc)
 * 时间 2023/1/17 13:51
 */
public interface SingleConditionalSnippet extends ConditionalSnippet {

    String snippet(BatisColumnAttribute columnAttribute);
}
